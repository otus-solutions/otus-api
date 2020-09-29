package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonPUTRequestUtility;
import br.org.otus.gateway.resource.OutcomesMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OutcomeGatewayService.class})
public class OutcomeGatewayServiceTest {
  private static final String ACTIVITY_ID = "5f5fade308a0fc339325a8c8";
  private static final String EVENT_ID = "6f5fade308a0fc339325a8c9";
  private OutcomeGatewayService service = PowerMockito.spy(new OutcomeGatewayService());

  @Mock
  private URL mockUrl;
  @Mock
  private OutcomesMicroServiceResources outcomesMicroServiceResources;
  @Mock
  private JsonPUTRequestUtility jsonPUTRequestUtility;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void cancelParticipantEventByActivityIdMethod_should_simulate_microserviceResponse() throws Exception {
    whenNew(OutcomesMicroServiceResources.class).withAnyArguments().thenReturn(outcomesMicroServiceResources);
    when(outcomesMicroServiceResources.cancelParticipantEventByActivityId(ACTIVITY_ID)).thenReturn(mockUrl);
    whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    assertTrue(service.cancelParticipantEventByActivityId(ACTIVITY_ID) instanceof GatewayResponse);
  }

  @Test(expected = ReadRequestException.class)
  public void cancelParticipantEventByActivityIdMethod_should_catch_IOException() throws Exception {
    whenNew(OutcomesMicroServiceResources.class).withAnyArguments().thenReturn(outcomesMicroServiceResources);
    when(outcomesMicroServiceResources.cancelParticipantEventByActivityId(ACTIVITY_ID)).thenReturn(mockUrl);
    whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    Mockito.when(jsonPUTRequestUtility.finish()).thenThrow(new IOException(new Throwable()));
    service.cancelParticipantEventByActivityId(ACTIVITY_ID);
  }

  @Test
  public void accomplishedParticipantEventMethod_should_simulate_microserviceResponse() throws Exception {
    whenNew(OutcomesMicroServiceResources.class).withAnyArguments().thenReturn(outcomesMicroServiceResources);
    when(outcomesMicroServiceResources.getAccomplishedParticipantEventAddress(EVENT_ID)).thenReturn(mockUrl);
    whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    assertTrue(service.accomplishedParticipantEvent(EVENT_ID) instanceof GatewayResponse);
  }

  @Test(expected = ReadRequestException.class)
  public void accomplishedParticipantEventMethod_should_catch_IOException() throws Exception {
    whenNew(OutcomesMicroServiceResources.class).withAnyArguments().thenReturn(outcomesMicroServiceResources);
    when(outcomesMicroServiceResources.getAccomplishedParticipantEventAddressByActivity(EVENT_ID)).thenReturn(mockUrl);
    whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    Mockito.when(jsonPUTRequestUtility.finish()).thenThrow(new IOException(new Throwable()));
    service.accomplishedParticipantEvent(EVENT_ID);
  }

  @Test
  public void accomplishedParticipantEventByActivityMethod_should_simulate_microserviceResponse() throws Exception {
    whenNew(OutcomesMicroServiceResources.class).withAnyArguments().thenReturn(outcomesMicroServiceResources);
    when(outcomesMicroServiceResources.getAccomplishedParticipantEventAddressByActivity(ACTIVITY_ID)).thenReturn(mockUrl);
    whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    assertTrue(service.accomplishedParticipantEventByActivity(ACTIVITY_ID) instanceof GatewayResponse);
  }

  @Test
  public void reopenedParticipantEventByActivityMethod_should_simulate_microserviceResponse() throws Exception {
    whenNew(OutcomesMicroServiceResources.class).withAnyArguments().thenReturn(outcomesMicroServiceResources);
    when(outcomesMicroServiceResources.getReopenedParticipantEventAddressByActivity(ACTIVITY_ID)).thenReturn(mockUrl);
    whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    assertTrue(service.reopenedParticipantEventByActivity(ACTIVITY_ID) instanceof GatewayResponse);
  }
}