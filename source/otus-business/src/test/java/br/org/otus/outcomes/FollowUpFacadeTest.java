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
import org.powermock.modules.junit4.PowerMockRunner;

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
    public void checkForParticipantEventCreation_should_return_true_when_activity_is_autofill() {
      Mockito.when(surveyActivity.getMode()).thenReturn(ActivityMode.AUTOFILL);

      boolean result = followUpFacade.checkForParticipantEventCreation(surveyActivity);
      Assert.assertTrue(result);
    }

    @Test
    public void checkForParticipantEventCreation_should_return_false_when_activity_is_not_autofill() {
      Mockito.when(surveyActivity.getMode()).thenReturn(ActivityMode.ONLINE);

      boolean result = followUpFacade.checkForParticipantEventCreation(surveyActivity);
      Assert.assertFalse(result);
    }

    @Test
    public void createParticipantActivityAutoFillEvent() {
      Mockito.when(participant.getRecruitmentNumber()).thenReturn(RN);
      Mockito.when(surveyActivity.getParticipantData()).thenReturn(participant);

      ObjectId objectId = new ObjectId();
      Mockito.when(participantFacade.findIdByRecruitmentNumber(RN)).thenReturn(objectId);
      Mockito.when(participantFacade.getByRecruitmentNumber(RN)).thenReturn(participant);

      Mockito.when(surveyActivity.getSurveyForm()).thenReturn(surveyForm);
      Mockito.when(surveyActivity.getActivityID()).thenReturn(new ObjectId());
      Mockito.when(surveyForm.getAcronym()).thenReturn(ACRONYM);


      followUpFacade.createParticipantActivityAutoFillEvent(surveyActivity);
    }
}