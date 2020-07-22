package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonPUTRequestUtility;
import br.org.otus.gateway.resource.OutcomesMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import org.bson.types.ObjectId;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OutcomeGatewayService.class})
public class OutcomeGatewayServiceTest {

  private OutcomeGatewayService service = PowerMockito.spy(new OutcomeGatewayService());
  private String activityId;
  @Mock
  private URL mockUrl;
  @Mock
  private OutcomesMicroServiceResources outcomesMicroServiceResources;
  @Mock
  private JsonPUTRequestUtility jsonPUTRequestUtility;

  @Before
  public void setUp() throws Exception {
    activityId = new ObjectId("5f1844d60d7b3e017612f47d").toString();
  }

  @Test
  public void cancelParticipantEventByActivityIdMethod_should_simulate_microserviceResponse() throws Exception {
    whenNew(OutcomesMicroServiceResources.class).withAnyArguments().thenReturn(outcomesMicroServiceResources);
    when(outcomesMicroServiceResources.cancelParticipantEventByActivityId(activityId)).thenReturn(mockUrl);
    whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    assertTrue(service.cancelParticipantEventByActivityId(activityId) instanceof GatewayResponse);
  }

  @Test(expected = ReadRequestException.class)
  public void cancelParticipantEventByActivityIdMethod_should_catch_IOException() throws Exception {
    whenNew(OutcomesMicroServiceResources.class).withAnyArguments().thenReturn(outcomesMicroServiceResources);
    when(outcomesMicroServiceResources.cancelParticipantEventByActivityId(activityId)).thenReturn(mockUrl);
    whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    Mockito.when(jsonPUTRequestUtility.finish()).thenThrow(new IOException(new Throwable()));
    service.cancelParticipantEventByActivityId(activityId);
  }
}