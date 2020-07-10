//package br.org.otus.report.templateReport;
//
//import br.org.otus.gateway.gates.OutcomeGatewayService;
//import br.org.otus.gateway.gates.ReportGatewayService;
//import br.org.otus.gateway.response.GatewayResponse;
//import br.org.otus.participant.api.ParticipantFacade;
//import org.bson.types.ObjectId;
//import org.ccem.otus.model.survey.activity.SurveyActivity;
//import org.ccem.otus.participant.model.Participant;
//import org.ccem.otus.service.ReportService;
//import org.ccem.otus.survey.form.SurveyForm;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import javax.inject.Inject;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(ReportGatewayService.class)
//public class TemplateReportFacadeTest {
//
//  private Long RN = 123L;
//  private String REPORT_ID = "5be30a2616da480067523dfd";
//  private String ACRONYM = "ACRONYM";
//  private ObjectId OID = new ObjectId();
//
//  @InjectMocks
//  private TemplateReportFacade templateReportFacade;
//
//  @InjectMocks
//  private ReportService reportService;
//
//  @Mock
//  private ParticipantFacade participantFacade;
//  @Mock
//  private ReportGatewayService reportGatewayService;
//  @Mock
//  private SurveyActivity surveyActivity;
//  @Mock
//  private SurveyForm surveyForm;
//  @Mock
//  private Participant participant;
//  @Mock
//  private GatewayResponse gatewayResponse;
//
//
//  @Before
//  public void setUp() throws Exception {
//    Mockito.when(participant.getRecruitmentNumber()).thenReturn(RN);
//    Mockito.when(surveyActivity.getParticipantData()).thenReturn(participant);
//
//    Mockito.when(participantFacade.findIdByRecruitmentNumber(RN)).thenReturn(OID);
//    Mockito.when(participantFacade.getByRecruitmentNumber(RN)).thenReturn(participant);
//
//    Mockito.when(surveyActivity.getSurveyForm()).thenReturn(surveyForm);
//    Mockito.when(surveyActivity.getActivityID()).thenReturn(new ObjectId());
//    Mockito.when(surveyForm.getAcronym()).thenReturn(ACRONYM);
//
//
//    //this is not working. Method startParticipantEvent is still being called
//    OutcomeGatewayService mock = Mockito.mock(OutcomeGatewayService.class);
//    PowerMockito.when(mock.startParticipantEvent(Mockito.anyString(), Mockito.anyString())).thenReturn(gatewayResponse);
//    PowerMockito.whenNew(OutcomeGatewayService.class).withNoArguments().thenReturn(mock);
//    //
//
//  }
//
//  @Test
//  public void createParticipantActivityAutoFillEvent() throws Exception {
//    templateReportFacade.getReportTemplate(RN, REPORT_ID);
//    Mockito.verify(reportGatewayService, Mockito.times(1)).getReportTemplate("");
//    Mockito.verify(reportService, Mockito.times(1)).getParticipantReport(RN, REPORT_ID);
//  }
//}