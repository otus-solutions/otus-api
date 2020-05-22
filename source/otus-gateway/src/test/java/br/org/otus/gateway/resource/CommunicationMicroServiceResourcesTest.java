package br.org.otus.gateway.resource;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.gateway.MicroservicesEnvironments;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CommunicationMicroServiceResources.class})
public class CommunicationMicroServiceResourcesTest {
  private static String HOST = "localhost";
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

  private CommunicationMicroServiceResources communicationMicroServiceResources;
  private MicroservicesEnvironments microservicesEnvironments;
  private URL url;

  @Before
  public void setUp() throws Exception {
    microservicesEnvironments = MicroservicesEnvironments.COMMUNICATION_SERVICE;
    mockStatic(System.class);
    when(System.getenv(microservicesEnvironments.getHost())).thenReturn(HOST);
    when(System.getenv(microservicesEnvironments.getPort())).thenReturn(PORT);

    communicationMicroServiceResources = new CommunicationMicroServiceResources();
  }

  @Test
  public void getMessageCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/message-communication/" + ID);
    assertEquals(communicationMicroServiceResources.getMessageCommunicationAddress(ID), url);
  }

  @Test
  public void getMessageByIdCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/find-list-message-communication/" + ID);
    assertEquals(communicationMicroServiceResources.getMessageByIdCommunicationAddress(ID), url);
  }

  @Test
  public void getMessageByIdLimitCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/find-list-message-limit-communication/" + ID + "/" + LIMIT);
    assertEquals(communicationMicroServiceResources.getMessageByIdLimitCommunicationAddress(ID, LIMIT), url);
  }

  @Test
  public void getIssueCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/issue-create");
    assertEquals(communicationMicroServiceResources.getIssueCommunicationAddress(), url);
  }

  @Test
  public void getUpdateReopenCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/update-issue-reopen-communication/" + ID);
    assertEquals(communicationMicroServiceResources.getUpdateReopenCommunicationAddress(ID), url);
  }

  @Test
  public void getUpdateCloseCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/update-issue-close-communication/" + ID);
    assertEquals(communicationMicroServiceResources.getUpdateCloseCommunicationAddress(ID), url);
  }

  @Test
  public void getListIssueCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/issue-list/" + ID);
    assertEquals(communicationMicroServiceResources.getListIssueCommunicationAddress(ID), url);
  }

}