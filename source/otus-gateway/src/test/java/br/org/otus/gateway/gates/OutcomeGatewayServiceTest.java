package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.resource.OutcomesMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//TODO - Teste de se revisado apos integração com micro serviço
@RunWith(PowerMockRunner.class)
@PrepareForTest({ OutcomeGatewayService.class })
public class OutcomeGatewayServiceTest {
  private static String INFO_VARIABLE_PARAMS = "{}";
  private static String CURRENT_VARIABLES_BY_MICROSERVICE = "";
  private static String HOST = "http://localhost:";
  private static String PORT = "8081";

  @InjectMocks
  private OutcomeGatewayService outcomeGatewayService;

  @Mock
  private JsonPOSTUtility jsonPOSTUtility;

  @Mock
  private URL requestURL;

  @Mock
  private File file;

  private OutcomesMicroServiceResources outcomesMicroServiceResources = PowerMockito.spy(new OutcomesMicroServiceResources());

  @Test
  public void createOutcome_should_create_outcome() throws Exception {
    PowerMockito.whenNew(OutcomesMicroServiceResources.class).withNoArguments().thenReturn(outcomesMicroServiceResources);
    Whitebox.setInternalState(outcomesMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(outcomesMicroServiceResources, "PORT", PORT);

    PowerMockito.when(outcomesMicroServiceResources.getCreateOutcomeAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenReturn(CURRENT_VARIABLES_BY_MICROSERVICE);

    final GatewayResponse response = outcomeGatewayService.createOutcome(INFO_VARIABLE_PARAMS);
    assertEquals(CURRENT_VARIABLES_BY_MICROSERVICE, response.getData());

    verify(jsonPOSTUtility, times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void createOutcome_should_throw_exception_for_IOException() throws Exception {
    PowerMockito.whenNew(OutcomesMicroServiceResources.class).withNoArguments().thenReturn(outcomesMicroServiceResources);
    Whitebox.setInternalState(outcomesMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(outcomesMicroServiceResources, "PORT", PORT);

    PowerMockito.when(outcomesMicroServiceResources.getCreateOutcomeAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenThrow(new IOException());
    outcomeGatewayService.createOutcome(INFO_VARIABLE_PARAMS);
  }

  @Test
  public void updateOutcome_should_update_outcome() throws Exception {
    PowerMockito.whenNew(OutcomesMicroServiceResources.class).withNoArguments().thenReturn(outcomesMicroServiceResources);
    Whitebox.setInternalState(outcomesMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(outcomesMicroServiceResources, "PORT", PORT);

    PowerMockito.when(outcomesMicroServiceResources.getCreateOutcomeAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenReturn(CURRENT_VARIABLES_BY_MICROSERVICE);

    final GatewayResponse response = outcomeGatewayService.updateOutcome(INFO_VARIABLE_PARAMS);
    assertEquals(CURRENT_VARIABLES_BY_MICROSERVICE, response.getData());

    verify(jsonPOSTUtility, times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void updateOutcome_should_throw_exception_for_IOException() throws Exception {
    PowerMockito.whenNew(OutcomesMicroServiceResources.class).withNoArguments().thenReturn(outcomesMicroServiceResources);
    Whitebox.setInternalState(outcomesMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(outcomesMicroServiceResources, "PORT", PORT);

    PowerMockito.when(outcomesMicroServiceResources.getCreateOutcomeAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenThrow(new IOException());
    outcomeGatewayService.updateOutcome(INFO_VARIABLE_PARAMS);
  }

  @Test
  public void listOutcomes_should_bring_outcomes() throws Exception {
    PowerMockito.whenNew(OutcomesMicroServiceResources.class).withNoArguments().thenReturn(outcomesMicroServiceResources);
    Whitebox.setInternalState(outcomesMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(outcomesMicroServiceResources, "PORT", PORT);

    PowerMockito.when(outcomesMicroServiceResources.getCreateOutcomeAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenReturn(CURRENT_VARIABLES_BY_MICROSERVICE);

    final GatewayResponse response = outcomeGatewayService.listOutcomes(INFO_VARIABLE_PARAMS);
    assertEquals(CURRENT_VARIABLES_BY_MICROSERVICE, response.getData());

    verify(jsonPOSTUtility, times(1)).finish();
  }


  @Test(expected = ReadRequestException.class)
  public void listOutcomes_should_throw_exception_for_IOException() throws Exception {
    PowerMockito.whenNew(OutcomesMicroServiceResources.class).withNoArguments().thenReturn(outcomesMicroServiceResources);
    Whitebox.setInternalState(outcomesMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(outcomesMicroServiceResources, "PORT", PORT);

    PowerMockito.when(outcomesMicroServiceResources.getCreateOutcomeAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenThrow(new IOException());
    outcomeGatewayService.listOutcomes(INFO_VARIABLE_PARAMS);
  }

}
