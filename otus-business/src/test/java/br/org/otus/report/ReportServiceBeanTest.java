package br.org.otus.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceResult;
import org.ccem.otus.model.dataSources.dcm.ultrasound.DCMUltrasoundDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSourceResult;
import org.ccem.otus.model.survey.activity.User;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantServiceBean;
import org.ccem.otus.persistence.ActivityDataSourceDao;
import org.ccem.otus.persistence.ParticipantDataSourceDao;
import org.ccem.otus.persistence.ReportDao;
import org.ccem.otus.persistence.ReportTemplateDTO;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.org.otus.gateway.resource.DBDistributionMicroServiceResources;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ParticipantServiceBean.class, Participant.class, ReportTemplate.class })
public class ReportServiceBeanTest {
  String REPORTID = "5a9199056ddc4f48a340b3ec";
  Long RECRUITMENT_NUMBER = (Long) 322148795L;
  String USER_MAIL = "teste@gmail.com";
  private static final String REPORT_UPDATE = "{\"data\":\"{\\\"template\\\" : \\\"\\u003cspan\\u003e\\u003c/span\\u003e\\\",\\\"label\\\": \\\"tiago\\\",\\\"fieldCenter\\\": [],\\\"dataSources\\\" : [{\\\"key\\\" : \\\"HS\\\",\\\"label\\\": \\\"tester\\\", \\\"dataSource\\\" : \\\"Participant\\\",\\\"filters\\\" : {\\\"statusHistory\\\" : {\\\"name\\\" : \\\"FINALIZED\\\",\\\"position\\\" : -1},\\\"acronym\\\" : \\\"TF\\\",\\\"category\\\" : \\\"C0\\\"}}]}\"}";

  @InjectMocks
  private ReportServiceBean reportServiceBean;

  @Mock
  private ReportTemplate reportTemplate;

  private String template = "<span>teste</span>";

  @Mock
  private ReportDao reportDao;

  @Mock
  private ObjectId reportObjectId = new ObjectId(REPORTID);

  @Mock
  private ParticipantDataSourceDao participantDataSourceDao;

  @Mock
  private ActivityDataSourceDao activityDataSourceDao;

  @Mock
  private ParticipantServiceBean participantServiceBean;

  @Mock
  private DBDistributionMicroServiceResources resources;

  @Test
  public void method_find_report_by_id_instanceof_ParticipantDataSource() throws Exception {
    ParticipantDataSource participantDataSource = new ParticipantDataSource();
    Whitebox.setInternalState(participantDataSource, "dataSource", "Participant");
    reportTemplate = new ReportTemplate();
    Whitebox.setInternalState(reportTemplate, "template", template);
    Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
    reportTemplate.getDataSources().add(participantDataSource);
    FieldCenter fieldCenterInstance = new FieldCenter();
    Whitebox.setInternalState(fieldCenterInstance, "acronym", "RS");
    ImmutableDate immutableDateInstance = new ImmutableDate("2018-02-22 00:00:00.000");
    ParticipantDataSourceResult participantDataSourceResult = new ParticipantDataSourceResult();
    Whitebox.setInternalState(participantDataSourceResult, "name", "Joao");
    Whitebox.setInternalState(participantDataSourceResult, "sex", "masc");
    Whitebox.setInternalState(participantDataSourceResult, "recruitmentNumber", (long) 123456789);
    Whitebox.setInternalState(participantDataSourceResult, "fieldCenter", fieldCenterInstance);
    Whitebox.setInternalState(participantDataSourceResult, "birthdate", immutableDateInstance);
    when(participantDataSourceDao.getResult(RECRUITMENT_NUMBER, participantDataSource)).thenReturn(participantDataSourceResult);
    when(reportDao.findReport(reportObjectId)).thenReturn(reportTemplate);
    ReportTemplate response = reportServiceBean.getParticipantReport(RECRUITMENT_NUMBER, REPORTID);
    assertTrue(response.getDataSources().get(0).getResult().get(0) instanceof ParticipantDataSourceResult);
    assertEquals(participantDataSourceResult.toString(), response.getDataSources().get(0).getResult().get(0).toString());
  }

  @Test
  public void method_find_report_by_id_instanceof_activity_data_source() throws Exception {
    ActivityDataSource activityDataSource = new ActivityDataSource();
    Whitebox.setInternalState(activityDataSource, "dataSource", "Activity");
    reportTemplate = new ReportTemplate();
    Whitebox.setInternalState(reportTemplate, "template", template);
    Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
    reportTemplate.getDataSources().add(activityDataSource);
    ActivityStatusOptions activityStatusOptions = ActivityStatusOptions.CREATED;
    User user = new User();
    Whitebox.setInternalState(user, "name", "Joao");
    ActivityStatus activityStatus = new ActivityStatus();
    Whitebox.setInternalState(activityStatus, "objectType", "ActivityStatus");
    Whitebox.setInternalState(activityStatus, "name", activityStatusOptions);
    Whitebox.setInternalState(activityStatus, "date", LocalDateTime.now());
    Whitebox.setInternalState(activityStatus, "user", user);
    ArrayList<ActivityStatus> statusHistory = new ArrayList<>();
    statusHistory.add(activityStatus);
    ActivityDataSourceResult activityDataSourceResult = new ActivityDataSourceResult();
    when(reportDao.findReport(reportObjectId)).thenReturn(reportTemplate);
    when(activityDataSourceDao.getResult(RECRUITMENT_NUMBER, activityDataSource)).thenReturn(activityDataSourceResult);
    ReportTemplate response = reportServiceBean.getParticipantReport(RECRUITMENT_NUMBER, REPORTID);
    assertTrue(response.getDataSources().get(0).getResult().get(0) instanceof ActivityDataSourceResult);
    assertEquals(activityDataSourceResult.toString(), response.getDataSources().get(0).getResult().get(0).toString());
  }

  @Test
  public void method_getReportByParticipant_should_returns_list_reports() throws Exception {
    ReportTemplate reportTemplate = new ReportTemplate();
    Participant participant = new Participant(RECRUITMENT_NUMBER);
    FieldCenter fieldCenter = new FieldCenter();
    fieldCenter.setAcronym("SP");
    participant.setFieldCenter(fieldCenter);
    ArrayList<ReportTemplateDTO> reports = new ArrayList<>();
    Whitebox.setInternalState(reportTemplate, "_id", reportObjectId);
    Whitebox.setInternalState(reportTemplate, "label", "teste");
    reports.add(new ReportTemplateDTO(reportTemplate));
    PowerMockito.when(participantServiceBean.getByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);
    PowerMockito.when(reportDao.getByCenter("SP")).thenReturn(reports);
    assertEquals(reports, reportServiceBean.getReportByParticipant(RECRUITMENT_NUMBER));
  }

  @Test
  public void metho_create_should_insert_new_report() throws Exception {
    ReportTemplate reportTemplate = new ReportTemplate();
    mockStatic(ReportTemplate.class);
    PowerMockito.when(ReportTemplate.class, "deserialize", Mockito.any()).thenReturn(reportTemplate);
    PowerMockito.when(ReportTemplate.class, "serialize", Mockito.any()).thenReturn(REPORT_UPDATE);
    reportTemplate = ReportTemplate.deserialize(REPORT_UPDATE);
    PowerMockito.when(reportDao.insert(Mockito.anyObject())).thenReturn(reportTemplate);
    assertEquals(reportTemplate, reportServiceBean.create(reportTemplate));
  }

  @Test
  public void method_delete_should_remove_report() throws Exception {
    PowerMockito.doNothing().when(reportDao, "deleteById", REPORTID);
    reportServiceBean.delete(REPORTID);
    Mockito.verify(reportDao, Mockito.times(1)).deleteById(REPORTID);
  }

  @Test
  public void method_list_should_returns_list_reports() throws ValidationException {
    ReportTemplate reportTemplate = new ReportTemplate();
    ArrayList<String> fieldCenter = new ArrayList<>();
    ArrayList<ReportDataSource> dataSources = new ArrayList<>();
    fieldCenter.add("SP");
    dataSources = reportTemplate.getDataSources();
    List<ReportTemplate> reports = new ArrayList<>();
    Whitebox.setInternalState(reportTemplate, "_id", reportObjectId);
    Whitebox.setInternalState(reportTemplate, "label", "RELATORIO");
    Whitebox.setInternalState(reportTemplate, "template", "<span></span>");
    Whitebox.setInternalState(reportTemplate, "fieldCenter", fieldCenter);
    Whitebox.setInternalState(reportTemplate, "dataSources", dataSources);
    reports.add(reportTemplate);
    PowerMockito.when(reportDao.getAll()).thenReturn(reports);
    assertEquals(reports, reportServiceBean.list());
  }

  @Test
  public void method_getById_should_return_report() throws DataNotFoundException, ValidationException {
    ReportTemplate reportTemplate = new ReportTemplate();
    ArrayList<String> fieldCenter = new ArrayList<>();
    ArrayList<ReportDataSource> dataSources = new ArrayList<>();
    fieldCenter.add("SP");
    dataSources = reportTemplate.getDataSources();
    List<ReportTemplate> reports = new ArrayList<>();
    Whitebox.setInternalState(reportTemplate, "_id", reportObjectId);
    Whitebox.setInternalState(reportTemplate, "label", "teste");
    Whitebox.setInternalState(reportTemplate, "template", "<span></span>");
    Whitebox.setInternalState(reportTemplate, "fieldCenter", fieldCenter);
    Whitebox.setInternalState(reportTemplate, "dataSources", dataSources);
    reports.add(reportTemplate);
    PowerMockito.when(reportDao.getById(REPORTID)).thenReturn(reportTemplate);
    assertEquals(reportTemplate, reportServiceBean.getByID(REPORTID));
  }

  @Test
  public void method_update_should_alter_report() throws Exception {
    ReportTemplate reportTemplate = new ReportTemplate();
    ReportTemplate updateReport = new ReportTemplate();
    ArrayList<String> fieldCenter = new ArrayList<>();
    ArrayList<ReportDataSource> dataSources = new ArrayList<>();
    fieldCenter.add("SP");
    dataSources = reportTemplate.getDataSources();
    Whitebox.setInternalState(reportTemplate, "_id", reportObjectId);
    Whitebox.setInternalState(reportTemplate, "label", "teste");
    Whitebox.setInternalState(reportTemplate, "template", "<span></span>");
    Whitebox.setInternalState(reportTemplate, "fieldCenter", fieldCenter);
    Whitebox.setInternalState(reportTemplate, "dataSources", dataSources);
    Whitebox.setInternalState(updateReport, "_id", reportObjectId);
    Whitebox.setInternalState(updateReport, "label", "Novo Template");
    Whitebox.setInternalState(updateReport, "template", "<h1></h1>");
    Whitebox.setInternalState(updateReport, "fieldCenter", fieldCenter);
    Whitebox.setInternalState(updateReport, "dataSources", dataSources);
    mockStatic(ReportTemplate.class);
    PowerMockito.when(ReportTemplate.class, "deserialize", Mockito.any()).thenReturn(updateReport);
    PowerMockito.when(ReportTemplate.class, "serialize", Mockito.any()).thenReturn(REPORT_UPDATE);
    PowerMockito.when(reportDao.updateFieldCenters(Mockito.anyObject())).thenReturn(updateReport);
    assertEquals(updateReport, reportServiceBean.updateFieldCenters(reportTemplate));
  }

  @Ignore
  @Test
  public void getParticipantReport_method_when_receive_instance_of_DCMUltrasoundDataSource_should_call_buildFilterToUltrasound_method() throws Exception {
    DCMUltrasoundDataSource mock = Mockito.mock(DCMUltrasoundDataSource.class);
    ArrayList<ReportDataSource> datasource = new ArrayList<>();
    datasource.add(mock);
    ReportTemplate reportTemplate = new ReportTemplate();

    Whitebox.setInternalState(reportTemplate, "dataSources", datasource);
    when(reportDao.findReport(reportObjectId)).thenReturn(reportTemplate);
    PowerMockito.when(DBDistributionMicroServiceResources.class, "getUltrasoundImageAddress").thenReturn(new URL("localhost:8080/api/ultrasound"));

    reportServiceBean.getParticipantReport(RECRUITMENT_NUMBER, REPORTID);

    Mockito.verify(((DCMUltrasoundDataSource) datasource.get(0))).buildFilterToUltrasound(RECRUITMENT_NUMBER);
  }

  @Ignore
  @Test
  public void getParticipantReport_method_when_receive_data_source_of_Retinography_then_should_process_with_expected() throws Exception {
    DCMUltrasoundDataSource mock = Mockito.mock(DCMUltrasoundDataSource.class);
    ArrayList<ReportDataSource> datasource = new ArrayList<>();
    datasource.add(mock);
    ReportTemplate reportTemplate = new ReportTemplate();

    when(reportDao.findReport(reportObjectId)).thenReturn(reportTemplate);
  }

}
