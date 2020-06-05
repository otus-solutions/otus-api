package br.org.otus.communication.message;

import br.org.otus.communication.MessageCommunicationFacade;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.gateway.response.exception.RequestException;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AuthorizationHeaderReader.class})
public class MessageCommunicationResourceTest {
  private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final String AUTHORIZATION_HEADER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
  private static final String ID = "5e0658135b4ff40f8916d2b5";
  private static final String LIMIT = "12";
  private static final String EMAIL = "email@email.com";
  private static final String MESSAGE_JSON = "{\n" +
    "\"objectType\": \"Issue\",\n" +
    "\"sender\": \"email do token\",\n" +
    "\"title\": \"Não consigo preencher a atividade TCLEC\",\n" +
    "\"message\": \"Quando tento responder uma pergunta, não consigo inserir a resposta\",\n" +
    "\"creationDate\": \"22/01/20\",\n" +
    "\"status\": \"OPEN\"\n" +
    "}";

  @InjectMocks
  private MessageCommunicationResource messageCommunicationResource;

  @Mock
  private MessageCommunicationFacade messageCommunicationFacade;
  @Mock
  private HttpServletRequest request;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private SessionIdentifier sessionIdentifier;
  @Mock
  private AuthenticationData authenticationData;

  JsonParser parser = new JsonParser();
  String returnData = "{data:null}";
  Object confirmed = parser.parse("{\"data\":\"{data:null}\"}");

  @Test
  public void createIssue_method_should_call_Facade_method_createIssue() throws Exception {
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
    mockStatic(AuthorizationHeaderReader.class);
    when(AuthorizationHeaderReader.class, "readToken", TOKEN).thenReturn(AUTHORIZATION_HEADER_TOKEN);
    when(securityContext.getSession(AUTHORIZATION_HEADER_TOKEN)).thenReturn(sessionIdentifier);
    when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(EMAIL);
    when(messageCommunicationFacade.createIssue(Mockito.any(), Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.createIssue(request, MESSAGE_JSON));
  }

  @Test
  public void createMessage_method_should_call_Facade_method_createMessage() throws Exception {
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
    mockStatic(AuthorizationHeaderReader.class);
    when(AuthorizationHeaderReader.class, "readToken", TOKEN).thenReturn(AUTHORIZATION_HEADER_TOKEN);
    when(securityContext.getSession(AUTHORIZATION_HEADER_TOKEN)).thenReturn(sessionIdentifier);
    when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(EMAIL);
    when(messageCommunicationFacade.createMessage(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.createMessage(request, ID, MESSAGE_JSON));
  }


  @Test
  public void filter_method_should_call_Facade_method_filter() throws Exception {
    when(messageCommunicationFacade.filter(Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.filter(Mockito.any()));
  }

  @Test
  public void updateReopen_method_should_call_Facade_method_updateReopen() throws Exception {
    when(messageCommunicationFacade.updateReopen(Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.updateReopen(ID));
  }

  @Test
  public void updateClose_method_should_call_Facade_method_updateClose() throws Exception {
    when(messageCommunicationFacade.updateClose(Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.updateClose(ID));
  }

  @Test
  public void updateFinalize_method_should_call_Facade_method_updateFinalize() throws Exception {
    when(messageCommunicationFacade.updateFinalize(Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.updateFinalize(ID));
  }

  @Test
  public void getMessageById_method_should_call_Facade_method_getMessageById() throws Exception {
    when(messageCommunicationFacade.getMessageById(Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.getMessageById(ID));
  }

  @Test
  public void getMessageByIdLimit_method_should_call_Facade_method_getMessageByIdLimit() throws Exception {
    when(messageCommunicationFacade.getMessageByIdLimit(Mockito.any(), Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.getMessageByIdLimit(ID,LIMIT));
  }

  @Test
  public void listIssue_method_should_call_Facade_method_getMessageByIdLimit() throws Exception {
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
    mockStatic(AuthorizationHeaderReader.class);
    when(AuthorizationHeaderReader.class, "readToken", TOKEN).thenReturn(AUTHORIZATION_HEADER_TOKEN);
    when(securityContext.getSession(AUTHORIZATION_HEADER_TOKEN)).thenReturn(sessionIdentifier);
    when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(EMAIL);
    when(messageCommunicationFacade.listIssue(Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.listIssue(request));
  }

  @Test
  public void getSenderById_method_should_call_Facade_method_getSenderById() throws Exception {
    when(messageCommunicationFacade.getSenderById(Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.getSenderById(ID));
  }

  @Test
  public void getIssuesByRn_method_should_call_Facade_method_getIssuesByRn() throws Exception {
    when(messageCommunicationFacade.getIssuesByRn(Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.getIssuesByRn(ID));
  }

  @Test
  public void getIssuesById_method_should_call_Facade_method_getIssuesById() throws Exception {
    when(messageCommunicationFacade.getIssuesById(Mockito.any())).thenReturn(returnData);
    assertEquals(confirmed.toString(), messageCommunicationResource.getIssuesById(ID));
  }

}