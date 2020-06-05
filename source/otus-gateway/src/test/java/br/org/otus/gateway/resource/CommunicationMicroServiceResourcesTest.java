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
    url = new URL("http://localhost:53004/api/messages/" + ID);
    assertEquals(communicationMicroServiceResources.getMessageCommunicationAddress(ID), url);
  }

  @Test
  public void getMessageByIdCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/list-messages/" + ID);
    assertEquals(communicationMicroServiceResources.getMessageByIdCommunicationAddress(ID), url);
  }

  @Test
  public void getMessageByIdLimitCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/list-messages-limit/" + ID + "/" + LIMIT);
    assertEquals(communicationMicroServiceResources.getMessageByIdLimitCommunicationAddress(ID, LIMIT), url);
  }

  @Test
  public void getIssueCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/issues");
    assertEquals(communicationMicroServiceResources.getIssuesCommunicationAddress(), url);
  }

  @Test
  public void getUpdateReopenCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/issues-reopen/" + ID);
    assertEquals(communicationMicroServiceResources.getUpdateReopenCommunicationAddress(ID), url);
  }

  @Test
  public void getUpdateCloseCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/issues-close/" + ID);
    assertEquals(communicationMicroServiceResources.getUpdateCloseCommunicationAddress(ID), url);
  }

  @Test
  public void getUpdateFinalizeCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/issues-finalize/" + ID);
    assertEquals(communicationMicroServiceResources.getUpdateFinalizeCommunicationAddress(ID), url);
  }

  @Test
  public void getListIssueCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/issues-list/" + ID);
    assertEquals(communicationMicroServiceResources.getListIssueCommunicationAddress(ID), url);
  }

  @Test
  public void getIssueByIdCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/issues/" + ID);
    assertEquals(communicationMicroServiceResources.getIssueByIdCommunicationAddress(ID), url);
  }

  @Test
  public void getIssueByRnCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://localhost:53004/api/issues-rn/" + ID);
    assertEquals(communicationMicroServiceResources.getIssueByRnCommunicationAddress(ID), url);
  }
}