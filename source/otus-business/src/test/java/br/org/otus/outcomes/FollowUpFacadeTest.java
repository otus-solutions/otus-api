package br.org.otus.outcomes;

import br.org.otus.gateway.gates.OutcomeGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FollowUpFacade.class)
public class FollowUpFacadeTest {
  private static final String FINALIZED_STATUS_HISTORY = "FINALIZED";
  private static final String REOPENED_STATUS_HISTORY = "REOPENED";
  private String activityId = "5f5fade308a0fc339325a8c8";

  @InjectMocks
  private FollowUpFacade followUpFacade;
  @Mock
  private OutcomeGatewayService outcomeGatewayService;
  private GatewayResponse response;

  @Before
  public void setUp() throws Exception {
    activityId = new ObjectId("5f1844d60d7b3e017612f47d").toString();
    response = new GatewayResponse().buildSuccess();
    whenNew(OutcomeGatewayService.class).withAnyArguments().thenReturn(outcomeGatewayService);
  }

  @Test
  public void cancelParticipantEventByActivityIdMethod_should_return_response_by_OutcomeGatewayService() throws Exception {
    when(outcomeGatewayService.cancelParticipantEventByActivityId(activityId))
      .thenReturn(response);
    assertEquals(followUpFacade.cancelParticipantEventByActivityId(activityId), response);
  }

  @Test
  public void statusUpdateEvent_should_select_mechanism_for_the_finishedEvent() throws Exception {
    followUpFacade.statusUpdateEvent(FINALIZED_STATUS_HISTORY, activityId );
    verify(outcomeGatewayService, Mockito.times(1)).accomplishedParticipantEventByActivity(activityId);
  }

  @Test
  public void statusUpdateEvent_should_select_mechanism_for_the_reopenedEvent() throws Exception {
    followUpFacade.statusUpdateEvent(REOPENED_STATUS_HISTORY, activityId );
    verify(outcomeGatewayService, Mockito.times(1)).reopenedParticipantEventByActivity(activityId);
  }


}

