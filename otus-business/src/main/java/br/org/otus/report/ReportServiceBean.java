package br.org.otus.report;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.dcm.retinography.DCMRetinographyDataSource;
import org.ccem.otus.model.dataSources.dcm.retinography.DCMRetinographyDataSourceResult;
import org.ccem.otus.model.dataSources.dcm.ultrasound.DCMUltrasoundDataSource;
import org.ccem.otus.model.dataSources.dcm.ultrasound.DCMUltrasoundDataSourceResult;
import org.ccem.otus.model.dataSources.exam.ExamDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.persistence.ActivityDataSourceDao;
import org.ccem.otus.persistence.ExamDataSourceDao;
import org.ccem.otus.persistence.ParticipantDataSourceDao;
import org.ccem.otus.persistence.ReportDao;
import org.ccem.otus.persistence.ReportTemplateDTO;
import org.ccem.otus.service.ReportService;

import br.org.otus.gateway.gates.DBDistributionGateway;
import br.org.otus.gateway.resource.DBDistributionMicroServiceResources;
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

  @Override
  public ReportTemplate getParticipantReport(Long recruitmentNumber, String reportId) throws DataNotFoundException, ValidationException, MalformedURLException {
    ObjectId reportObjectId = new ObjectId(reportId);
    ReportTemplate report = reportDao.findReport(reportObjectId);
    for (ReportDataSource<?> dataSource : report.getDataSources()) {
      if (dataSource instanceof ParticipantDataSource) {
        ((ParticipantDataSource) dataSource).getResult().add(participantDataSourceDao.getResult(recruitmentNumber, (ParticipantDataSource) dataSource));
      } else if (dataSource instanceof ActivityDataSource) {
        ((ActivityDataSource) dataSource).getResult().add(activityDataSourceDao.getResult(recruitmentNumber, (ActivityDataSource) dataSource));
      } else if (dataSource instanceof ExamDataSource) {
        ((ExamDataSource) dataSource).getResult().add(examDataSourceDao.getResult(recruitmentNumber, (ExamDataSource) dataSource));
      } else if (dataSource instanceof DCMRetinographyDataSource) {
        String filter = ((DCMRetinographyDataSource) dataSource).buildFilterToRetinography(recruitmentNumber);
        URL url = new DBDistributionMicroServiceResources().getRetinographyImageAddress();
        GatewayResponse response = new DBDistributionGateway().find(filter, url);
        ((DCMRetinographyDataSource) dataSource).getResult().add(DCMRetinographyDataSourceResult.deserialize((String) response.getData()));
      } else if (dataSource instanceof DCMUltrasoundDataSource) {
        String filter = ((DCMUltrasoundDataSource) dataSource).buildFilterToUltrasound(recruitmentNumber);
        URL url = new DBDistributionMicroServiceResources().getUltrasoundImageAddress();
        GatewayResponse response = new DBDistributionGateway().find(filter, url);
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

}
