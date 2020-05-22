package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonGETUtility;
import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.request.JsonPUTRequestUtility;
import br.org.otus.gateway.resource.CommunicationMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CommunicationGatewayService.class})
public class CommunicationGatewayServiceTest {
  private static String HOST = "http://localhost:";
  private static String PORT = "53004";
  private static final String ID = "5e0658135b4ff40f8916d2b5";
  private static final String LIMIT = "12";
  private static final String EMAIL = "email@email.com";
  private static final String MESSAGE_ISSUE_JSON = "{\n" +
    "\"objectType\": \"Issue\",\n" +
    "\"emailReporter\": \"email do token\",\n" +
    "\"title\": \"Não consigo preencher a atividade TCLEC\",\n" +
    "\"message\": \"Quando tento responder uma pergunta, não consigo inserir a resposta\",\n" +
    "\"creationDate\": \"22/01/20\",\n" +
    "\"status\": \"OPEN\"\n" +
    "}";
  private static final String MESSAGE_JSON = "{\n" +
    "\"text\": \"Segunda mensagem: não entendi sua pergunta\",\n" +
    "\"sender\": \"email do token\",\n" +
    "\"id\":\"9247c29478234234\"\n" +
    "}";

  @InjectMocks
  private CommunicationGatewayService communicationGatewayService;

  @Mock
  private JsonPOSTUtility jsonPOSTUtility;

  @Mock
  private JsonPUTRequestUtility jsonPUTRequestUtility;

  @Mock
  private JsonGETUtility jsonGETUtility;

  @Mock
  private URL requestURL;

  @Mock
  private GatewayResponse gatewayResponse;

  private CommunicationMicroServiceResources communicationMicroServiceResources = PowerMockito.spy(new CommunicationMicroServiceResources());

  String returnData = "{data:null}";
  boolean confirmed = true;

  @Before
  public void setUp() throws Exception {
    PowerMockito.whenNew(CommunicationMicroServiceResources.class).withNoArguments().thenReturn(communicationMicroServiceResources);
    Whitebox.setInternalState(communicationMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(communicationMicroServiceResources, "PORT", PORT);
  }

  @Test
  public void createIssue_method_should_call_service_communication_getIssueCommunicationAddress() throws Exception{
    PowerMockito.when(communicationMicroServiceResources.getIssueCommunicationAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenReturn(returnData);

    gatewayResponse = communicationGatewayService.createIssue(MESSAGE_ISSUE_JSON);

    assertEquals(returnData,  gatewayResponse.getData());

    verify(jsonPOSTUtility, times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void createIssue_method_should_throw_exception_for_IOException() throws Exception{
    PowerMockito.when(jsonPOSTUtility.finish()).thenThrow(new IOException(new Throwable("Message")));
    communicationGatewayService.createIssue(MESSAGE_ISSUE_JSON);
  }

  @Test
  public void createMessage_method_should_call_service_communication_getMessageCommunicationAddress() throws Exception{
    PowerMockito.when(communicationMicroServiceResources.getMessageCommunicationAddress(ID)).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenReturn(returnData);

    gatewayResponse = communicationGatewayService.createMessage(ID, MESSAGE_JSON);

    assertEquals(returnData,  gatewayResponse.getData());

    verify(jsonPOSTUtility, times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void createMessage_method_should_throw_exception_for_IOException() throws Exception{
    PowerMockito.when(jsonPOSTUtility.finish()).thenThrow(new IOException(new Throwable("Message")));
    communicationGatewayService.createMessage(ID, MESSAGE_JSON);
  }

  @Test
  public void updateReopen_method_should_call_service_communication_getUpdateReopenCommunicationAddress() throws Exception{
    PowerMockito.when(communicationMicroServiceResources.getUpdateReopenCommunicationAddress(ID)).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    PowerMockito.when(jsonPUTRequestUtility.finish()).thenReturn(returnData);

    gatewayResponse = communicationGatewayService.updateReopen(ID);

    assertEquals(confirmed,  gatewayResponse.getData());

    verify(jsonPUTRequestUtility, times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void updateReopen_method_should_throw_exception_for_IOException() throws Exception{
    PowerMockito.when(jsonPUTRequestUtility.finish()).thenThrow(new IOException(new Throwable("Message")));
    communicationGatewayService.updateReopen(ID);
  }

  @Test
  public void updateClose_method_should_call_service_communication_getUpdateCloseCommunicationAddress() throws Exception{
    PowerMockito.when(communicationMicroServiceResources.getUpdateCloseCommunicationAddress(ID)).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTRequestUtility);
    PowerMockito.when(jsonPUTRequestUtility.finish()).thenReturn(returnData);

    gatewayResponse = communicationGatewayService.updateClose(ID);

    assertEquals(confirmed,  gatewayResponse.getData());

    verify(jsonPUTRequestUtility, times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void updateClose_method_should_throw_exception_for_IOException() throws Exception{
    PowerMockito.when(jsonPUTRequestUtility.finish()).thenThrow(new IOException(new Throwable("Message")));
    communicationGatewayService.updateClose(ID);
  }

  @Test
  public void getMessageById_method_should_call_service_communication_getMessageByIdCommunicationAddress() throws Exception{
    PowerMockito.when(communicationMicroServiceResources.getMessageByIdCommunicationAddress(ID)).thenReturn(requestURL);

    PowerMockito.whenNew(JsonGETUtility.class).withAnyArguments().thenReturn(jsonGETUtility);
    PowerMockito.when(jsonGETUtility.finish()).thenReturn(returnData);

    gatewayResponse = communicationGatewayService.getMessageById(ID);

    assertEquals(returnData,  gatewayResponse.getData());

    verify(jsonGETUtility, times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void getMessageById_method_should_throw_exception_for_IOException() throws Exception{
    PowerMockito.when(jsonGETUtility.finish()).thenThrow(new IOException(new Throwable("Message")));
    communicationGatewayService.getMessageById(ID);
  }

  @Test
  public void getMessageByIdLimit_method_should_call_service_communication_getMessageByIdLimitCommunicationAddress() throws Exception{
    PowerMockito.when(communicationMicroServiceResources.getMessageByIdLimitCommunicationAddress(ID, LIMIT)).thenReturn(requestURL);

    PowerMockito.whenNew(JsonGETUtility.class).withAnyArguments().thenReturn(jsonGETUtility);
    PowerMockito.when(jsonGETUtility.finish()).thenReturn(returnData);

    gatewayResponse = communicationGatewayService.getMessageByIdLimit(ID, LIMIT);

    assertEquals(returnData,  gatewayResponse.getData());

    verify(jsonGETUtility, times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void getMessageByIdLimit_method_should_throw_exception_for_IOException() throws Exception{
    PowerMockito.when(jsonGETUtility.finish()).thenThrow(new IOException(new Throwable("Message")));
    communicationGatewayService.getMessageByIdLimit(ID, LIMIT);
  }

  @Test
  public void listIssue_method_should_call_service_communication_getMessageByIdLimitCommunicationAddress() throws Exception{
    PowerMockito.when(communicationMicroServiceResources.getListIssueCommunicationAddress(EMAIL)).thenReturn(requestURL);

    PowerMockito.whenNew(JsonGETUtility.class).withAnyArguments().thenReturn(jsonGETUtility);
    PowerMockito.when(jsonGETUtility.finish()).thenReturn(returnData);

    gatewayResponse = communicationGatewayService.listIssue(EMAIL);

    assertEquals(returnData,  gatewayResponse.getData());

    verify(jsonGETUtility, times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void listIssue_method_should_throw_exception_for_IOException() throws Exception{
    PowerMockito.when(jsonGETUtility.finish()).thenThrow(new IOException(new Throwable("Message")));
    communicationGatewayService.listIssue(EMAIL);
  }

}