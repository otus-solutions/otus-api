package br.org.otus.report;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.ActivityReportTemplate;
import org.ccem.otus.model.ReportTemplate;
import org.ccem.otus.model.dataSources.ReportDataSource;
import org.ccem.otus.persistence.ReportTemplateDTO;
import org.ccem.otus.service.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.google.gson.GsonBuilder;

import br.org.otus.security.user.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ReportTemplate.class, AuthorizationHeaderReader.class, GsonBuilder.class })
public class ReportResourceTest {

	private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
	private static final String AUTHORIZATION_HEADER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
	private static final Long recruitmentNumber = (Long) 5001007L;
	private static final String label = "teste";
	private static final String PARTICIPANT_LIST = "{\"data\":[{\"_id\":\"5ab128d713cdd20490497f58\",\"label\":\"teste\"}]}";
	private static final String REPORT_ID = "5ab3a88013cdd20490873afe";
	private static final String reportUploadJson = "{\"template\" : \"<span></span>\",\"label\": \"tiago\",\"fieldCenter\": [],\"dataSources\" : [{\"key\" : \"HS\",\"label\": \"tester\", \"dataSource\" : \"Participant\",\"filters\" : {\"statusHistory\" : {\"name\" : \"FINALIZED\",\"position\" : -1},\"acronym\" : \"TF\",\"category\" : \"C0\"}}]}";
	private static final String reportJson = "{\"_id\":{\"$oid\":\"5ab128d713cdd20490497f58\"},\"template\":\"\\u003cspan\\u003e\\u003c/span\\u003e\",\"label\":\"teste\",\"fieldCenter\":[\"SP\"]}";
	private static final String USER_MAIL = "otus@otus.com";
	private static final String RESULT = "{\"data\":true}";
	private static final String REPORT_BY_RN = "{\"data\":{\"_id\":\"5ab128d713cdd20490497f58\",\"objectType\":null,\"template\":null,\"label\":\"teste\",\"sender\":null,\"sendingDate\":null,\"fieldCenter\":null,\"dataSources\":null}}";
	private static final Object REPORTS = "{\"data\":[{\"_id\":{\"$oid\":\"5ab128d713cdd20490497f58\"},\"template\":\"<span></span>\",\"label\":\"teste\",\"fieldCenter\":[\"SP\"]}]}";
	private static final Object REPORTS_BY_ID = "{\"data\":{\"_id\":{\"$oid\":\"5ab128d713cdd20490497f58\"},\"template\":\"<span></span>\",\"label\":\"teste\",\"fieldCenter\":[\"SP\"]}}";
	private static final Object REPORT_UPDATE = "{\"data\":{\"_id\":{\"$oid\":\"5ab128d713cdd20490497f58\"},\"template\":\"<h1></h1>\",\"label\":\"Novo Template\",\"fieldCenter\":[\"SP\"]}}";
	private static final Object REPORTS_ACTIVITY = "{\"data\":{\"acronym\":null,\"versions\":null,\"_id\":\"5ab128d713cdd20490497f58\",\"objectType\":null,\"template\":null,\"label\":null,\"sender\":null,\"sendingDate\":null,\"fieldCenter\":null,\"dataSources\":null}}";
	private static final Object REPORTS_ACTIVITY_LIST = "{\"data\":[{\"acronym\":\"ACTA\",\"versions\":null,\"_id\":null,\"objectType\":null,\"template\":null,\"label\":null,\"sender\":null,\"sendingDate\":null,\"fieldCenter\":null,\"dataSources\":null}]}";
	private static final Object REPORTS_ACTIVITY_UPDATE = "{\"data\":true}";
	private static final String ACRONYM = "ACTA";
	private ReportTemplate report = PowerMockito.spy(new ReportTemplate());

  @InjectMocks
  private ReportResource reportResource;
  @Mock
  private ReportFacade reportFacade;
  @Mock
  private ReportService reportService;
  @Mock
  private ReportServiceBean reportServiceBean;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private HttpServletRequest request;
  @Mock
  private SessionIdentifier sessionIdentifier;
  @Mock
  private AuthenticationData authenticationData;

  private ReportTemplate reportTemplate;

  private ActivityReportTemplate activityReportTemplate;

  List<ReportTemplate> reports = new ArrayList<>();

  ObjectId id = new ObjectId("5ab128d713cdd20490497f58");

  @Test
  public void method_getParticipantReport_should_return_report_byRecruitmentNumber() throws DataNotFoundException {
    reportTemplate = new ReportTemplate();
    Whitebox.setInternalState(reportTemplate, "_id", id);
    Whitebox.setInternalState(reportTemplate, "label", label);
    when(reportFacade.getParticipantReport(recruitmentNumber, REPORT_ID)).thenReturn(reportTemplate);
    assertEquals(REPORT_BY_RN, reportResource.getParticipantReport(recruitmentNumber, REPORT_ID));
  }

  @Test
  public void method_getActivityReport_should_return_report_activity() throws DataNotFoundException {
    activityReportTemplate = new ActivityReportTemplate();
    Whitebox.setInternalState(activityReportTemplate, "_id", id);
    when(reportFacade.getActivityReport(REPORT_ID)).thenReturn(activityReportTemplate);
    assertEquals(REPORTS_ACTIVITY, reportResource.getActivityReport(REPORT_ID));
  }

  @Test
  public void method_listByParticipant_should_returns_datasourceLists() throws DataNotFoundException {
    List<ReportTemplateDTO> reportTemplateDTOs = new ArrayList<>();
    reportTemplate = new ReportTemplate();
    Whitebox.setInternalState(reportTemplate, "_id", id);
    Whitebox.setInternalState(reportTemplate, "label", label);
    reportTemplateDTOs.add(new ReportTemplateDTO(reportTemplate));
    when(reportFacade.getReportByParticipant(recruitmentNumber)).thenReturn(reportTemplateDTOs);
    assertEquals(PARTICIPANT_LIST, reportResource.listByParticipant(recruitmentNumber));
  }

  @Test
  public void method_create_should_insert_reportTemplate() throws Exception {
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
    mockStatic(AuthorizationHeaderReader.class);
    when(AuthorizationHeaderReader.class, "readToken", TOKEN).thenReturn(AUTHORIZATION_HEADER_TOKEN);
    when(securityContext.getSession(AUTHORIZATION_HEADER_TOKEN)).thenReturn(sessionIdentifier);
    when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(USER_MAIL);
    reportTemplate = new ReportTemplate();
    Whitebox.setInternalState(reportTemplate, "_id", id);
    when(reportFacade.create(reportUploadJson, USER_MAIL)).thenReturn(reportTemplate);
    assertEquals("{\"data\":{\"_id\":{\"$oid\":\"5ab128d713cdd20490497f58\"}}}", reportResource.create(request, reportUploadJson));
  }

  @Test
  public void method_delete_should_remove_report() throws Exception {
    PowerMockito.doNothing().when(reportFacade, "deleteById", REPORT_ID);
    assertEquals(RESULT, reportResource.delete(REPORT_ID));
  }

  @Test
  public void method_list_should_returns_list_reports() {
    reportTemplate = new ReportTemplate();
    ArrayList<String> fieldCenter = new ArrayList<>();
    ArrayList<ReportDataSource> dataSources = new ArrayList<>();
    fieldCenter.add("SP");
    dataSources = reportTemplate.getDataSources();
    Whitebox.setInternalState(reportTemplate, "_id", id);
    Whitebox.setInternalState(reportTemplate, "label", label);
    Whitebox.setInternalState(reportTemplate, "template", "<span></span>");
    Whitebox.setInternalState(reportTemplate, "fieldCenter", fieldCenter);
    Whitebox.setInternalState(reportTemplate, "dataSources", dataSources);
    reports.add(reportTemplate);
    when(reportFacade.list()).thenReturn(reports);
    assertEquals(REPORTS, reportResource.list());
  }

  @Test
  public void method_getById_should_return_report() {
    reportTemplate = new ReportTemplate();
    ArrayList<String> fieldCenter = new ArrayList<>();
    ArrayList<ReportDataSource> dataSources = new ArrayList<>();
    fieldCenter.add("SP");
    dataSources = reportTemplate.getDataSources();
    Whitebox.setInternalState(reportTemplate, "_id", id);
    Whitebox.setInternalState(reportTemplate, "label", label);
    Whitebox.setInternalState(reportTemplate, "template", "<span></span>");
    Whitebox.setInternalState(reportTemplate, "fieldCenter", fieldCenter);
    Whitebox.setInternalState(reportTemplate, "dataSources", dataSources);
    reports.add(reportTemplate);
    when(reportFacade.getById(REPORT_ID)).thenReturn(reportTemplate);
    assertEquals(REPORTS_BY_ID, reportResource.getById(REPORT_ID));
  }

  @Test
  public void method_update_should_alter_report() throws Exception {
    reportTemplate = new ReportTemplate();
    ReportTemplate updateReport = new ReportTemplate();
    ArrayList<String> fieldCenter = new ArrayList<>();
    ArrayList<ReportDataSource> dataSources = new ArrayList<>();
    fieldCenter.add("SP");
    dataSources = report.getDataSources();
    Whitebox.setInternalState(report, "_id", id);
    Whitebox.setInternalState(report, "label", label);
    Whitebox.setInternalState(report, "template", "<span></span>");
    Whitebox.setInternalState(report, "fieldCenter", fieldCenter);
    Whitebox.setInternalState(report, "dataSources", dataSources);

		Whitebox.setInternalState(updateReport, "_id", id);
		Whitebox.setInternalState(updateReport, "label", "Novo Template");
		Whitebox.setInternalState(updateReport, "template", "<h1></h1>");
		Whitebox.setInternalState(updateReport, "fieldCenter", fieldCenter);
		Whitebox.setInternalState(updateReport, "dataSources", dataSources);
		PowerMockito.when(reportFacade.updateFieldCenters(Mockito.anyObject())).thenReturn(updateReport);
		assertEquals(REPORT_UPDATE, reportResource.updateFieldCenters(Mockito.anyObject()));
	}

	@Test
	public void method_create_should_insert_activityReportTemplate() throws Exception {
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
		mockStatic(AuthorizationHeaderReader.class);
		when(AuthorizationHeaderReader.class, "readToken", TOKEN).thenReturn(AUTHORIZATION_HEADER_TOKEN);
		when(securityContext.getSession(AUTHORIZATION_HEADER_TOKEN)).thenReturn(sessionIdentifier);
		when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
		when(authenticationData.getUserEmail()).thenReturn(USER_MAIL);
		activityReportTemplate = new ActivityReportTemplate();
		Whitebox.setInternalState(activityReportTemplate, "_id", id);
		when(reportFacade.createActivityReport(reportUploadJson, USER_MAIL)).thenReturn(activityReportTemplate);

		assertEquals("{\"data\":{\"_id\":{\"$oid\":\"5ab128d713cdd20490497f58\"}}}", reportResource.createActivityReport(request, reportUploadJson));
	}

	@Test
	public void method_getActivityReportList_should_return_list_report_activity() {
		activityReportTemplate = new ActivityReportTemplate();
		List<ActivityReportTemplate> activityReportTemplates = new ArrayList<>();

		Whitebox.setInternalState(activityReportTemplate, "acronym", ACRONYM);

		activityReportTemplates.add(activityReportTemplate);

		when(reportFacade.getActivityReportList(ACRONYM)).thenReturn(activityReportTemplates);

		assertEquals(REPORTS_ACTIVITY_LIST, reportResource.getActivityReportList(ACRONYM));
	}

	@Test
	public void method_update_should_alter_activity_report() throws Exception {
		PowerMockito.doNothing().when(reportFacade,"updateActivityReport", Mockito.anyString(), Mockito.anyString());
		assertEquals(REPORTS_ACTIVITY_UPDATE, reportResource.updateActivityReport(Mockito.anyString(),Mockito.anyString()));
	}

}
