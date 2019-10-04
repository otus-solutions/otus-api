package org.ccem.otus.service;

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
import org.ccem.otus.model.dataSources.exam.ExamDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.persistence.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

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

    @Override
    public ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException, ValidationException {
        ObjectId reportObjectId = new ObjectId(reportId);
        ReportTemplate report = reportDao.findReport(reportObjectId);

        fillReport(recruitmentNumber, report);

        return report;
    }

    @Override
    public ActivityReportTemplate getActivityReport(String activityID) throws DataNotFoundException, ValidationException {
        SurveyActivity activities;
        AnswerFillingDataSourceFilters filters;
        SurveyActivity activity = activityService.getByID(activityID);
        Long recruitmentNumber = activity.getParticipantData().getRecruitmentNumber();
        String acronym = activity.getSurveyForm().getAcronym();
        Integer version = activity.getSurveyForm().getVersion();

        ActivityReportTemplate report = reportDao.getActivityReport(acronym, version);

        if(activity.isFinalized()){
            for (ReportDataSource dataSource : report.getDataSources()) {
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
            throw new ValidationException(new Throwable("Activity with acronym {" + acronym + " and version " + version +"} not finalized."));
        }

        return report;
    }

    private ReportTemplate fillReport(Long recruitmentNumber, ReportTemplate report) throws DataNotFoundException {
        for (ReportDataSource dataSource : report.getDataSources()) {
            if (dataSource instanceof ParticipantDataSource) {
                ((ParticipantDataSource) dataSource).getResult()
                        .add(participantDataSourceDao.getResult(recruitmentNumber, (ParticipantDataSource) dataSource));
            } else if (dataSource instanceof ActivityDataSource) {
                ((ActivityDataSource) dataSource).getResult()
                        .add(activityDataSourceDao.getResult(recruitmentNumber, (ActivityDataSource) dataSource));
            } else if (dataSource instanceof ExamDataSource) {
                ((ExamDataSource) dataSource).getResult().add(examDataSourceDao.getResult(recruitmentNumber, (ExamDataSource) dataSource));
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

}
