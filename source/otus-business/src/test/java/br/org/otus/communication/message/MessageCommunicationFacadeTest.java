package br.org.otus.communication.message;

import br.org.otus.communication.MessageCommunicationFacade;
import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.response.exception.HttpResponseException;
import com.google.gson.JsonSyntaxException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CommunicationGatewayService.class)
public class MessageCommunicationFacadeTest {
  private static final String ID = "5e0658135b4ff40f8916d2b5";
  private static final String LIMIT = "12";
  private static final String EMAIL = "email@email.com";
  private static final String MESSAGE_JSON = "{\n" +
    "\"objectType\": \"Issue\",\n" +
    "\"emailReporter\": \"email do token\",\n" +
    "\"title\": \"Não consigo preencher a atividade TCLEC\",\n" +
    "\"message\": \"Quando tento responder uma pergunta, não consigo inserir a resposta\",\n" +
    "\"creationDate\": \"22/01/20\",\n" +
    "\"status\": \"OPEN\"\n" +
    "}";

  @InjectMocks
  private MessageCommunicationFacade messageCommunicationFacade;

  @Mock
  private GatewayResponse gatewayResponse;
  @Mock
  private CommunicationGatewayService communicationGatewayService;

  private String messageJson;

  private MalformedURLException requestException = PowerMockito.spy(new MalformedURLException());

  @Before
  public void setUp(){
    gatewayResponse = new GatewayResponse();
   }

  @Test
  @Ignore
  public void createIssue_method_should_() throws Exception {
    when(communicationGatewayService.createIssue(Mockito.any())).thenReturn(gatewayResponse);
    assertEquals(messageJson, messageCommunicationFacade.createIssue(EMAIL, MESSAGE_JSON));
  }

  @Test(expected = HttpResponseException.class)
  public void createIssue_method_should_DataFormatException() throws JsonSyntaxException, MalformedURLException, RequestException {
    PowerMockito.doThrow(requestException).when(communicationGatewayService).createIssue(Mockito.any());
    messageCommunicationFacade.createIssue(EMAIL, MESSAGE_JSON);
  }

  @Test(expected = HttpResponseException.class)
  public void createMessage_method_should_DataFormatException() throws JsonSyntaxException, MalformedURLException, RequestException {
    PowerMockito.doThrow(requestException).when(communicationGatewayService).createMessage(Mockito.any(), Mockito.any());
    messageCommunicationFacade.createMessage(ID, messageJson);
  }

  @Test(expected = HttpResponseException.class)
  public void updateReopen_method_should_DataFormatException() throws JsonSyntaxException, MalformedURLException, RequestException {
    PowerMockito.doThrow(requestException).when(communicationGatewayService).updateReopen(Mockito.any());
    messageCommunicationFacade.updateReopen(ID);
  }

  @Test(expected = HttpResponseException.class)
  public void updateClose_method_should_DataFormatException() throws JsonSyntaxException, MalformedURLException, RequestException {
    PowerMockito.doThrow(requestException).when(communicationGatewayService).updateClose(Mockito.any());
    messageCommunicationFacade.updateClose(ID);
  }

  @Test(expected = HttpResponseException.class)
  public void getMessageById_method_should_DataFormatException() throws JsonSyntaxException, MalformedURLException, RequestException {
    PowerMockito.doThrow(requestException).when(communicationGatewayService).getMessageById(Mockito.any());
    messageCommunicationFacade.getMessageById(ID);
  }

  @Test(expected = HttpResponseException.class)
  public void getMessageByIdLimit_method_should_DataFormatException() throws JsonSyntaxException, MalformedURLException, RequestException {
    PowerMockito.doThrow(requestException).when(communicationGatewayService).getMessageByIdLimit(Mockito.any(),Mockito.any());
    messageCommunicationFacade.getMessageByIdLimit(ID, LIMIT);
  }

  @Test(expected = HttpResponseException.class)
  public void listIssue_method_should_DataFormatException() throws JsonSyntaxException, MalformedURLException, RequestException {
    PowerMockito.doThrow(requestException).when(communicationGatewayService).listIssue(Mockito.any());
    messageCommunicationFacade.listIssue(EMAIL);
  }

}