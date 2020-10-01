package br.org.otus.outcomes;

import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.gates.OutcomeGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.survey.form.SurveyForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FollowUpFacade.class, Logger.class})
public class FollowUpFacadeTest {
  private static final String FINALIZED_STATUS_HISTORY = "FINALIZED";
  private static final String REOPENED_STATUS_HISTORY = "REOPENED";
  private static final String ACTIVITY_ID = "5f5fade308a0fc339325a8c8";

  @InjectMocks
  private FollowUpFacade followUpFacade;
  @Mock
  private OutcomeGatewayService outcomeGatewayService;
  private GatewayResponse response;
  @Mock
  private Participant participant;
  @Mock
  private SurveyActivity surveyActivity;
  @Mock
  private SurveyForm surveyForm;
  @Mock
  private CommunicationGatewayService communicationGatewayService;
  @Mock
  private GatewayResponse notification;
  @Mock
  private Logger logger;

  @Before
  public void setUp() throws Exception {
    response = new GatewayResponse().buildSuccess();
    whenNew(OutcomeGatewayService.class).withAnyArguments().thenReturn(outcomeGatewayService);
  }

  @Test
  public void cancelParticipantEventByActivityIdMethod_should_return_response_by_OutcomeGatewayService() throws Exception {
    when(outcomeGatewayService.cancelParticipantEventByActivityId(ACTIVITY_ID))
      .thenReturn(response);
    assertEquals(followUpFacade.cancelParticipantEventByActivityId(ACTIVITY_ID), response);
  }

  @Test
  public void statusUpdateEvent_should_select_mechanism_for_the_finishedEvent() throws Exception {
    followUpFacade.statusUpdateEvent(FINALIZED_STATUS_HISTORY, ACTIVITY_ID);
    verify(outcomeGatewayService, Mockito.times(1)).accomplishedParticipantEventByActivity(ACTIVITY_ID);
  }

  @Test
  public void statusUpdateEvent_should_select_mechanism_for_the_reopenedEvent() throws Exception {
    followUpFacade.statusUpdateEvent(REOPENED_STATUS_HISTORY, ACTIVITY_ID);
    verify(outcomeGatewayService, Mockito.times(1)).reopenedParticipantEventByActivity(ACTIVITY_ID);
  }


//  @Test
//  public void createParticipantActivityAutoFillEvent_should() throws Exception {
//    when(participant, "getEmail").thenReturn("otus@gmail.com");
//    when(surveyActivity, "getSurveyForm").thenReturn(surveyForm);
//    when(surveyForm, "getAcronym").thenReturn("TCLE");
//    whenNew(CommunicationGatewayService.class).withAnyArguments().thenReturn(communicationGatewayService);
//    PowerMockito.mockStatic(Logger.class);
//    when(communicationGatewayService, "sendMail", Mockito.any()).thenReturn(notification);
//    followUpFacade.sendAutoFillActivityNotificationEmail(participant, surveyActivity);
//    PowerMockito.verifyStatic(Logger.class, Mockito.times(2));
//  }
}