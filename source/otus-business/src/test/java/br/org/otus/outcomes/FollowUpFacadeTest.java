package br.org.otus.outcomes;

import br.org.otus.gateway.gates.OutcomeGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.participant.api.ParticipantFacade;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.survey.form.SurveyForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.ConstructorExpectationSetup;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.MalformedURLException;

@RunWith(PowerMockRunner.class)
public class FollowUpFacadeTest {
  private Long RN = 123L;
  private String ACRONYM = "ACRONYM";

  @InjectMocks
  private FollowUpFacade followUpFacade;


  @Mock
  private ParticipantFacade participantFacade;
  @Mock
  private OutcomeGatewayService outcomeGatewayService;

  @Mock
  private SurveyActivity surveyActivity;
  @Mock
  private SurveyForm surveyForm;
  @Mock
  private Participant participant;
  @Mock
  private GatewayResponse gatewayResponse;


  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void createParticipantActivityAutoFillEvent() throws MalformedURLException {
    Mockito.when(participant.getRecruitmentNumber()).thenReturn(RN);
    Mockito.when(surveyActivity.getParticipantData()).thenReturn(participant);

    ObjectId objectId = new ObjectId();
    Mockito.when(participantFacade.findIdByRecruitmentNumber(RN)).thenReturn(objectId);
    Mockito.when(participantFacade.getByRecruitmentNumber(RN)).thenReturn(participant);

    Mockito.when(surveyActivity.getSurveyForm()).thenReturn(surveyForm);
    Mockito.when(surveyActivity.getActivityID()).thenReturn(new ObjectId());
    Mockito.when(surveyForm.getAcronym()).thenReturn(ACRONYM);

    OutcomeGatewayService outcomeGatewayService = Mockito.mock(OutcomeGatewayService.class);
//      Mockito.when(outcomeGatewayService.startParticipantEvent(Mockito.eq(objectId.toString()), Mockito.anyString())).thenReturn(null);
//      Mockito.when(outcomeGatewayService.startParticipantEvent(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
//      Mockito.when(outcomeGatewayService.startParticipantEvent(Mockito.anyString(), {"objectType":"ParticipantActivityAutoFillEvent","name":"ACRONYM","acronym":"ACRONYM","activityId":"5eb4256816c30069eccb05ff","description":"ACRONYM"})).thenReturn(null);

    followUpFacade.createParticipantActivityAutoFillEvent(surveyActivity);
  }
}