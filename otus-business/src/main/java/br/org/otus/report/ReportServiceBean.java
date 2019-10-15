package br.org.otus.report;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ActivityReportTemplate;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityReportAnswerFillingDataSource;
import org.ccem.otus.model.dataSources.activity.AnswerFillingDataSource;
import org.ccem.otus.model.dataSources.activity.AnswerFillingDataSourceFilters;
import org.ccem.otus.model.dataSources.dcm.retinography.DCMRetinographyDataSource;
import org.ccem.otus.model.dataSources.dcm.retinography.DCMRetinographyDataSourceResult;
import org.ccem.otus.model.dataSources.dcm.ultrasound.DCMUltrasoundDataSource;
import org.ccem.otus.model.dataSources.dcm.ultrasound.DCMUltrasoundDataSourceResult;
import org.ccem.otus.model.dataSources.exam.ExamDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.persistence.ActivityDataSourceDao;
import org.ccem.otus.persistence.ExamDataSourceDao;
import org.ccem.otus.persistence.ParticipantDataSourceDao;
import org.ccem.otus.persistence.ReportDao;
import org.ccem.otus.persistence.ReportTemplateDTO;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.ReportService;

import br.org.otus.gateway.gates.DCMGatewayService;
import br.org.otus.gateway.response.GatewayResponse;

@Stateless
public class ReportServiceBean implements ReportService {

  @Inject
  private ReportDao reportDao;
  @Inject
  private ParticipantDataSourceDao participantDataSourceDao;
  @Inject
  private ActivityDataSourceDao activityDataSourceDao;
  @Inject
  private ParticipantService participantService;
  @Inject
  private ExamDataSourceDao examDataSourceDao;
  @Inject
  private ActivityService activityService;
  @Inject
  private DCMGatewayService gateway;

  @Override
  public ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException, ValidationException, MalformedURLException {
    ObjectId reportObjectId = new ObjectId(reportId);
    ReportTemplate report = reportDao.findReport(reportObjectId);

    fillReport(recruitmentNumber, report);

    return report;
  }

  @Override
  public ActivityReportTemplate getActivityReport(String activityID) throws DataNotFoundException, ValidationException, MalformedURLException {
    SurveyActivity activities;
    AnswerFillingDataSourceFilters filters;
    SurveyActivity activity = activityService.getByID(activityID);
    Long recruitmentNumber = activity.getParticipantData().getRecruitmentNumber();
    String acronym = activity.getSurveyForm().getAcronym();
    Integer version = activity.getSurveyForm().getVersion();

    ActivityReportTemplate report = reportDao.getActivityReport(acronym, version);

    if (activity.isFinalized()) {
      for (ReportDataSource<?> dataSource : report.getDataSources()) {
        if (dataSource instanceof AnswerFillingDataSource) {
          filters = ((AnswerFillingDataSource) dataSource).getFilters();
          try {
            activities = activityService.getActivity(filters.getAcronym(), filters.getVersion(), filters.getCategory(), recruitmentNumber);
          } catch (DataNotFoundException e) {
            activities = null;
          }

          ((AnswerFillingDataSource) dataSource).fillResult(activities);
        }
        if (dataSource instanceof ActivityReportAnswerFillingDataSource) {
          ((ActivityReportAnswerFillingDataSource) dataSource).fillResult(activity);
        }
      }
      fillReport(recruitmentNumber, report);
    } else {
      throw new ValidationException(new Throwable("Activity with acronym {" + acronym + " and version " + version + "} not finalized."));
    }

    return report;
  }

  private ReportTemplate fillReport(Long recruitmentNumber, ReportTemplate report) throws DataNotFoundException, MalformedURLException {
    for (ReportDataSource<?> dataSource : report.getDataSources()) {
      if (dataSource instanceof ParticipantDataSource) {
        ((ParticipantDataSource) dataSource).getResult().add(participantDataSourceDao.getResult(recruitmentNumber, (ParticipantDataSource) dataSource));
      } else if (dataSource instanceof ActivityDataSource) {
        ((ActivityDataSource) dataSource).getResult().add(activityDataSourceDao.getResult(recruitmentNumber, (ActivityDataSource) dataSource));
      } else if (dataSource instanceof ExamDataSource) {
        ((ExamDataSource) dataSource).getResult().add(examDataSourceDao.getResult(recruitmentNumber, (ExamDataSource) dataSource));
      } else if (dataSource instanceof DCMRetinographyDataSource) {
        String filter = ((DCMRetinographyDataSource) dataSource).buildFilterToRetinography(recruitmentNumber);
        GatewayResponse response = gateway.findRetinography(filter);
        ((DCMRetinographyDataSource) dataSource).getResult().addAll(DCMRetinographyDataSourceResult.deserializeList((String) response.getData()));
      } else if (dataSource instanceof DCMUltrasoundDataSource) {
        String filter = ((DCMUltrasoundDataSource) dataSource).buildFilterToUltrasound(recruitmentNumber);
        GatewayResponse response = gateway.findUltrasound(filter);
        ((DCMUltrasoundDataSource) dataSource).getResult().add(DCMUltrasoundDataSourceResult.deserialize((String) response.getData()));
      }
    }
    return report;
  }

  @Override
  public List<ReportTemplateDTO> getReportByParticipant(Long recruitmentNumber) throws DataNotFoundException, ValidationException {
    Participant participant = participantService.getByRecruitmentNumber(recruitmentNumber);
    String field = participant.getFieldCenter().getAcronym();
    return reportDao.getByCenter(field);
  }

  @Override
  public ReportTemplate create(ReportTemplate reportTemplate) {
    ReportTemplate insertedReport = reportDao.insert(reportTemplate);
    return insertedReport;
  }

  @Override
  public void delete(String id) throws DataNotFoundException {
    reportDao.deleteById(id);
  }

  @Override
  public List<ReportTemplate> list() throws ValidationException {
    return reportDao.getAll();
  }

  @Override
  public ReportTemplate getByID(String id) throws DataNotFoundException, ValidationException {
    return reportDao.getById(id);
  }

  @Override
  public ReportTemplate updateFieldCenters(ReportTemplate reportTemplate) throws DataNotFoundException {
    return reportDao.updateFieldCenters(reportTemplate);
  }

  @Override
  public ActivityReportTemplate createActivityReport(ActivityReportTemplate activityReportTemplate) throws ValidationException {
    return reportDao.insertActivityReport(activityReportTemplate);
  }

  @Override
  public List<ActivityReportTemplate> getActivityReportList(String acronym) throws DataNotFoundException {
    return reportDao.getActivityReportList(acronym);
  }

  @Override
  public void updateActivityReport(String activityId, String updateActivityReport) throws DataNotFoundException {
    ObjectId objectId = new ObjectId(activityId);
    ActivityReportTemplate activityReportTemplate = ActivityReportTemplate.deserialize(updateActivityReport);
    ArrayList<Integer> versions = activityReportTemplate.getVersions();

    reportDao.updateActivityReport(objectId, versions);
  }

}
