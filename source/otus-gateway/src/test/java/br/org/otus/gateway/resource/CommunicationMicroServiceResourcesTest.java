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
public class CommunicationMicroServiceResourcesTest extends MicroServiceResourcesTestParent {

  private static final String ID = "5e0658135b4ff40f8916d2b5";
  private static final String LIMIT = "12";
  private static final String SKIP = "0";
  private static final String ORDER = "asc";
  private static final String FILTER = "{\n" +
    "  \"currentQuantity\": 0,\n" +
    "  \"quantityToGet\": 10,\n" +
    "  \"order\": {\n" +
    "    \"fields\": [\n" +
    "      \"group\",\n" +
    "      \"sender\",\n" +
    "      \"creationDate\"\n" +
    "    ],\n" +
    "    \"mode\": -1 \n" +
    "  },\n" +
    "  \"filter\": {\n" +
    "    \"sender\": \"5\",\n" +
    "    \"group\": \"3\"\n" +
    "  }\n" +
    "}";

  private CommunicationMicroServiceResources communicationMicroServiceResources;

  @Before
  public void setUp() throws Exception {
    parentSetUp(MicroservicesEnvironments.COMMUNICATION_SERVICE);
    communicationMicroServiceResources = new CommunicationMicroServiceResources();
  }

  @Test
  public void getMessageCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/messages/" + ID);
    assertEquals(communicationMicroServiceResources.getMessageCommunicationAddress(ID), url);
  }

  @Test
  public void getMessageByIdCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/messages/" + ID);
    assertEquals(communicationMicroServiceResources.getMessageByIdCommunicationAddress(ID), url);
  }

  @Test
  public void getMessageByIdLimitCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/messages/limit/" + ID+ "/" + SKIP + "/" + LIMIT + "/" + ORDER);
    assertEquals(communicationMicroServiceResources.getMessageByIdLimitCommunicationAddress(ID, SKIP, LIMIT, ORDER), url);
  }

  @Test
  public void getIssueCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/issues");
    assertEquals(communicationMicroServiceResources.getIssuesCommunicationAddress(), url);
  }

  @Test
  public void getUpdateReopenCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/issues-reopen/" + ID);
    assertEquals(communicationMicroServiceResources.getUpdateReopenCommunicationAddress(ID), url);
  }

  @Test
  public void getUpdateCloseCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/issues-close/" + ID);
    assertEquals(communicationMicroServiceResources.getUpdateCloseCommunicationAddress(ID), url);
  }

  @Test
  public void getUpdateFinalizeCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/issues-finalize/" + ID);
    assertEquals(communicationMicroServiceResources.getUpdateFinalizeCommunicationAddress(ID), url);
  }

  @Test
  public void getIssueByIdCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/issues/" + ID);
    assertEquals(communicationMicroServiceResources.getIssueByIdCommunicationAddress(ID), url);
  }

  @Test
  public void getIssueByRnCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/issues/sender/" + ID);
    assertEquals(communicationMicroServiceResources.getIssueBySenderIdCommunicationAddress(ID), url);
  }

  @Test
  public void getIssueByFilterCommunicationAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/project-communication/issues/filter");
    assertEquals(communicationMicroServiceResources.getFilterCommunicationAddress(), url);
  }
}