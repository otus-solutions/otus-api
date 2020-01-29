package br.org.otus.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.ActivityReportTemplate;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceResult;
import org.ccem.otus.model.dataSources.activity.ActivityReportAnswerFillingDataSource;
import org.ccem.otus.model.dataSources.activity.AnswerFillingDataSource;
import org.ccem.otus.model.dataSources.activity.AnswerFillingDataSourceFilters;
import org.ccem.otus.model.dataSources.dcm.retinography.DCMRetinographyDataSource;
import org.ccem.otus.model.dataSources.dcm.retinography.RetinographyDataSourceFilter;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSource;
import org.ccem.otus.model.dataSources.participant.ParticipantDataSourceResult;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.User;
import org.ccem.otus.model.survey.activity.filling.FillContainer;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantServiceBean;
import org.ccem.otus.persistence.ActivityDataSourceDao;
import org.ccem.otus.persistence.ExamDataSourceDao;
import org.ccem.otus.persistence.ParticipantDataSourceDao;
import org.ccem.otus.persistence.ReportDao;
import org.ccem.otus.persistence.ReportTemplateDTO;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.org.otus.gateway.gates.DCMGatewayService;
import br.org.otus.gateway.resource.DBDistributionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ParticipantServiceBean.class, DCMGatewayService.class, Participant.class, ReportTemplate.class, GatewayResponse.class})
public class ReportServiceBeanTest {
  private static final String REPORT_UPDATE = "{\"data\":\"{\\\"template\\\" : \\\"\\u003cspan\\u003e\\u003c/span\\u003e\\\",\\\"label\\\": \\\"tiago\\\",\\\"fieldCenter\\\": [],\\\"dataSources\\\" : [{\\\"key\\\" : \\\"HS\\\",\\\"label\\\": \\\"tester\\\", \\\"dataSource\\\" : \\\"Participant\\\",\\\"filters\\\" : {\\\"statusHistory\\\" : {\\\"name\\\" : \\\"FINALIZED\\\",\\\"position\\\" : -1},\\\"acronym\\\" : \\\"TF\\\",\\\"category\\\" : \\\"C0\\\"}}]}\"}";
  private static final String ACRONYM = "ACTA";
  private static final Integer VERSION = 1;
  private static final String CATEGORY_NAME = "C0";
  private String REPORTID = "5a9199056ddc4f48a340b3ec";
  private Long RECRUITMENT_NUMBER = (Long) 322148795L;
  private String template = "<span>teste</span>";
  private String RESPONSE_TO_RETINOGRAPHY = "[{\"date\":\"2019-09-09T17:40:34.699Z\",eye\":\"left\",result\":\"R0lGODlhPQBEAPeoAJos595kzAP\"}]";
  private ReportTemplate reportTemplate;
  private ActivityReportAnswerFillingDataSource activityReportAnswerFillingDataSource;
  private ActivityReportTemplate activityReportTemplate;
  private AnswerFillingDataSourceFilters filters;
  private AnswerFillingDataSource answerFillingDataSource;

  @InjectMocks
  private ReportServiceBean reportServiceBean;
  @Mock
  private ReportDao reportDao;
  @Mock
  private ObjectId reportObjectId = new ObjectId(REPORTID);
  @Mock
  private ParticipantDataSourceDao participantDataSourceDao;
  @Mock
  private ActivityDataSourceDao activityDataSourceDao;
  @Mock
  private ParticipantServiceBean participantService;
  @Mock
  private ExamDataSourceDao examDataSourceDao;
  @Mock
  private SurveyForm surveyForm;
  @Mock
  private SurveyActivity activity;
  @Mock
  private ActivityService activityService;
  @Mock
  private DBDistributionMicroServiceResources resources;
  @Mock
  private DCMGatewayService gateway;

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
    PowerMockito.when(participantService.getByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);
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

  @Test
  public void getParticipantReport_method_should_return_report_with_result_of_retinography() throws Exception {
    GatewayResponse response = new GatewayResponse();
    Whitebox.setInternalState(response, "data", RESPONSE_TO_RETINOGRAPHY);
    RetinographyDataSourceFilter filter = new RetinographyDataSourceFilter();
    Whitebox.setInternalState(filter, "recruitmentNumber", Long.toString(RECRUITMENT_NUMBER));
    Whitebox.setInternalState(filter, "examName", "DCMRetinography");
    Whitebox.setInternalState(filter, "sending", 0);
    Whitebox.setInternalState(filter, "eyeSelected", "left");

    DCMRetinographyDataSource dataSource = new DCMRetinographyDataSource();
    Whitebox.setInternalState(dataSource, "dataSource", "DCMRetinography");
    Whitebox.setInternalState(dataSource, "filters", filter);

    reportTemplate = new ReportTemplate();
    Whitebox.setInternalState(reportTemplate, "template", template);
    Whitebox.setInternalState(reportTemplate, "dataSources", new ArrayList<>());
    reportTemplate.getDataSources().add(dataSource);

    mockStatic(DCMGatewayService.class);
    PowerMockito.when(reportDao.findReport(reportObjectId)).thenReturn(reportTemplate);
    PowerMockito.when(gateway.findRetinography(Mockito.anyString())).thenReturn(response);

    ReportTemplate template = reportServiceBean.getParticipantReport(RECRUITMENT_NUMBER, REPORTID);

    assertTrue(template.getDataSources().get(0) instanceof DCMRetinographyDataSource);
  }

  @Test
  public void getActivityReportMethod_report_by_id_instanceof_activity_report_data_source() throws Exception {

    Participant participant = new Participant(RECRUITMENT_NUMBER);

    ActivityStatusOptions activityStatusOptions = ActivityStatusOptions.FINALIZED;

    ActivityStatus activityStatus = new ActivityStatus();
    Whitebox.setInternalState(activityStatus, "objectType", "ActivityStatus");
    Whitebox.setInternalState(activityStatus, "name", activityStatusOptions);
    Whitebox.setInternalState(activityStatus, "date", LocalDateTime.now());

    ArrayList<ActivityStatus> statusHistory = new ArrayList<>();
    statusHistory.add(activityStatus);

    FillContainer fillContainer = new FillContainer();
    List<QuestionFill> fillingList = new ArrayList<>();
    QuestionFill questionFill = new QuestionFill();
    Whitebox.setInternalState(questionFill, "questionID", "ATCA4");

    fillingList.add(questionFill);

    Whitebox.setInternalState(fillContainer, "fillingList", fillingList);

    activity = new SurveyActivity();
    Whitebox.setInternalState(activity, "activityID", reportObjectId);
    Whitebox.setInternalState(activity, "surveyForm", surveyForm);
    Whitebox.setInternalState(activity, "participantData", participant);
    Whitebox.setInternalState(activity, "statusHistory", statusHistory);
    Whitebox.setInternalState(activity, "fillContainer", fillContainer);

    filters = new AnswerFillingDataSourceFilters();
    Whitebox.setInternalState(filters, "acronym", ACRONYM);
    Whitebox.setInternalState(filters, "version", VERSION);
    Whitebox.setInternalState(filters, "category", CATEGORY_NAME);

    answerFillingDataSource = new AnswerFillingDataSource();
    Whitebox.setInternalState(answerFillingDataSource, "dataSource", "AnswerFilling");
    Whitebox.setInternalState(answerFillingDataSource, "filters", filters);

    activityReportAnswerFillingDataSource = new ActivityReportAnswerFillingDataSource();
    Whitebox.setInternalState(answerFillingDataSource, "dataSource", "ActivityReportAnswerFilling");

    ArrayList<ReportDataSource> dataSources = new ArrayList<>();

    activityReportTemplate = new ActivityReportTemplate();

    ArrayList<Integer> versions = new ArrayList();
    versions.add(VERSION);

    dataSources.add(answerFillingDataSource);
    dataSources.add(activityReportAnswerFillingDataSource);

    Whitebox.setInternalState(activityReportTemplate, "template", template);
    Whitebox.setInternalState(activityReportTemplate, "acronym", ACRONYM);
    Whitebox.setInternalState(activityReportTemplate, "versions", versions);
    Whitebox.setInternalState(activityReportTemplate, "dataSources", new ArrayList<>());
    activityReportTemplate.getDataSources().add(answerFillingDataSource);

    List<SurveyItem> surveyItemList = new ArrayList<>();
    SurveyItem surveyItem = new SurveyItem();
    Whitebox.setInternalState(surveyItem, "customID", "ACTC2");
    Whitebox.setInternalState(surveyItem, "templateID", "ACTA2");

    surveyItemList.add(surveyItem);

    SurveyTemplate surveyTemplate = new SurveyTemplate();
    Whitebox.setInternalState(surveyTemplate, "itemContainer", surveyItemList);

    PowerMockito.when(surveyForm.getAcronym()).thenReturn(ACRONYM);
    PowerMockito.when(surveyForm.getVersion()).thenReturn(VERSION);
    PowerMockito.when(surveyForm.getSurveyTemplate()).thenReturn(surveyTemplate);
    PowerMockito.when(activityService.getByID(REPORTID)).thenReturn(activity);
    PowerMockito.when(reportDao.getActivityReport(ACRONYM, VERSION)).thenReturn(activityReportTemplate);
    PowerMockito.when(activityService.getActivity(filters.getAcronym(), filters.getVersion(), filters.getCategory(), activity.getParticipantData().getRecruitmentNumber())).thenReturn(activity);

    ActivityReportTemplate response = reportServiceBean.getActivityReport(REPORTID);

    assertTrue(response.getDataSources().get(0).getResult().get(0) instanceof QuestionFill);
  }

  @Test(expected = DataNotFoundException.class)
  public void method_getActivityReport_shoud_called_DataNotFoundException() throws Exception {
    when(activityService.getByID(REPORTID)).thenThrow(new DataNotFoundException(new Throwable("")));
    reportServiceBean.getActivityReport(REPORTID);
  }

  @Test(expected = DataNotFoundException.class)
  public void getActivityReportMethod_shoud_called_DataNotFoundException() throws Exception {
    when(activityService.getByID(REPORTID)).thenThrow(new DataNotFoundException(new Throwable("")));
    reportServiceBean.getActivityReport(REPORTID);
  }

  @Test
  public void createActivityReportMethod__should_insert_new_activity_report() throws Exception {
    activityReportTemplate = new ActivityReportTemplate();

    PowerMockito.when(reportDao.insertActivityReport(Mockito.anyObject())).thenReturn(activityReportTemplate);
    assertEquals(activityReportTemplate, reportServiceBean.createActivityReport(activityReportTemplate));
  }

  @Test
  public void getActivityReportListMethod_should_return_activity_report() throws Exception {
    activityReportTemplate = new ActivityReportTemplate();
    List<ActivityReportTemplate> activityReportTemplates = new ArrayList<>();
    activityReportTemplates.add(activityReportTemplate);

    PowerMockito.when(reportDao.getActivityReportList(Mockito.anyObject())).thenReturn(activityReportTemplates);

    assertEquals(activityReportTemplates, reportServiceBean.getActivityReportList(ACRONYM));
  }


  @Test
  public void updateActivityReportMethod_should_update_activity_report() throws Exception {
    activityReportTemplate = new ActivityReportTemplate();

    ArrayList<Integer> versions = new ArrayList();
    versions.add(VERSION);

    Whitebox.setInternalState(activityReportTemplate, "versions", versions);

    reportServiceBean.updateActivityReport(REPORTID, ActivityReportTemplate.serialize(activityReportTemplate));

    Mockito.verify(reportDao, Mockito.times(1)).updateActivityReport(reportObjectId, versions);
  }

}
