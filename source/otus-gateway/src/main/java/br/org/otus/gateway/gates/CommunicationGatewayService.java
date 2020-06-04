package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonDELETEUtility;
import br.org.otus.gateway.request.JsonGETUtility;
import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.request.JsonPUTRequestUtility;
import br.org.otus.gateway.resource.CommunicationMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CommunicationGatewayService {
  public GatewayResponse sendMail(String emailVariables) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getCommunicationAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, emailVariables);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse createCommunication(String emailTemplate) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getCreateCommunicationAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, emailTemplate);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse findCommunication(String emailTemplateId) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getFindCommunicationAddress(emailTemplateId);
    try {

      JsonGETUtility jsonGETUtility = new JsonGETUtility(requestURL);
      String response = jsonGETUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse getAllCommunication() throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getAllCommunicationAddress();
    try {
      JsonGETUtility jsonGETUtility = new JsonGETUtility(requestURL);
      String response = jsonGETUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse updateCommunication(String emailTemplate) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getUpdateCommunicationAddress();
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.writeBody(emailTemplate);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse deleteCommunication(String emailTemplateId) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getDeleteCommunicationAddress(emailTemplateId);
    try {
      JsonDELETEUtility jsonDELETE = new JsonDELETEUtility(requestURL);
      String response = jsonDELETE.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse createIssue(String issue) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getIssuesCommunicationAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, issue);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse createMessage(String id, String message) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getMessageCommunicationAddress(id);
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, message);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse filter(String filterJson) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getFilterCommunicationAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, filterJson);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse updateReopen(String issueId) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getUpdateReopenCommunicationAddress(issueId);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse updateClose(String issueId) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getUpdateCloseCommunicationAddress(issueId);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse updateFinalize(String issueId) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getUpdateFinalizeCommunicationAddress(issueId);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse getMessageById(String issueId) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getMessageByIdCommunicationAddress(issueId);
    try {
      JsonGETUtility jsonGET  = new JsonGETUtility(requestURL);
      String response = jsonGET.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse getMessageByIdLimit(String issueId, String limit) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getMessageByIdLimitCommunicationAddress(issueId, limit);
    try {
      JsonGETUtility jsonGET = new JsonGETUtility(requestURL);
      String response = jsonGET.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse listIssue(String id) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getListIssueCommunicationAddress(id);
    try {
      JsonGETUtility jsonGET = new JsonGETUtility(requestURL);
      String response = jsonGET.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse getIssueById(String id) throws MalformedURLException {
    URL requestURL = new CommunicationMicroServiceResources().getIssueByIdCommunicationAddress(id);
    try {
      JsonGETUtility jsonGET = new JsonGETUtility(requestURL);
      String response = jsonGET.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }
}
