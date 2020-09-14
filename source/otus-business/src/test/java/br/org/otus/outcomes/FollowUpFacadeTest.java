package br.org.otus.outcomes;

import br.org.otus.gateway.gates.OutcomeGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import netscape.javascript.JSObject;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FollowUpFacade.class)
public class FollowUpFacadeTest {
  private static final String FINALIZED_STATUS_HISTORY = "FINALIZED";
  private static final String REOPENED_STATUS_HISTORY = "REOPENED";
  private String activityId;

  @InjectMocks
  private FollowUpFacade followUpFacade;
  @Mock
  private OutcomeGatewayService outcomeGatewayService;
  private GatewayResponse response;
  private JSObject Mock;

  @Before
  public void setUp() throws Exception {
    activityId = new ObjectId("5f1844d60d7b3e017612f47d").toString();
    response = new GatewayResponse().buildSuccess();

  }

  @Test
  public void cancelParticipantEventByActivityIdMethod_should_return_response_by_OutcomeGatewayService() throws Exception {
    PowerMockito.whenNew(OutcomeGatewayService.class).withAnyArguments().thenReturn(outcomeGatewayService);
    PowerMockito.when(outcomeGatewayService.cancelParticipantEventByActivityId(activityId))
      .thenReturn(response);
    Assert.assertEquals(followUpFacade.cancelParticipantEventByActivityId(activityId), response);
  }


  @Test
  public void statusUpdateEventMethod_should(){
//    followUpFacade.statusUpdateEvent(statusHistory, );

  }
}

