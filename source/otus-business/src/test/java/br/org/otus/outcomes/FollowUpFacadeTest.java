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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.ConstructorExpectationSetup;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.MalformedURLException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OutcomeGatewayService.class)
public class FollowUpFacadeTest {
  private Long RN = 123L;
  private String ACRONYM = "ACRONYM";
  private ObjectId OID = new ObjectId();

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
    Mockito.when(participant.getRecruitmentNumber()).thenReturn(RN);
    Mockito.when(surveyActivity.getParticipantData()).thenReturn(participant);

    Mockito.when(participantFacade.findIdByRecruitmentNumber(RN)).thenReturn(OID);
    Mockito.when(participantFacade.getByRecruitmentNumber(RN)).thenReturn(participant);

    Mockito.when(surveyActivity.getSurveyForm()).thenReturn(surveyForm);
    Mockito.when(surveyActivity.getActivityID()).thenReturn(new ObjectId());
    Mockito.when(surveyForm.getAcronym()).thenReturn(ACRONYM);


    //this is not working. Method startParticipantEvent is still being called
    OutcomeGatewayService mock = Mockito.mock(OutcomeGatewayService.class);
    PowerMockito.when(mock.startParticipantEvent(Mockito.anyString(), Mockito.anyString())).thenReturn(gatewayResponse);
    PowerMockito.whenNew(OutcomeGatewayService.class).withNoArguments().thenReturn(mock);
    //

  }

  @Test
  @Ignore
  public void createParticipantActivityAutoFillEvent() throws Exception {
    followUpFacade.createParticipantActivityAutoFillEvent(surveyActivity);
  }
}