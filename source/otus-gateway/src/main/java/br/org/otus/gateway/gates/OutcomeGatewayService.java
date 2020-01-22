package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonGETUtility;
import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.request.JsonPUTRequestUtility;
import br.org.otus.gateway.resource.OutcomesMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class OutcomeGatewayService {

  public GatewayResponse createFollowUp(String followUpJson) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getCreateFollowUpAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, followUpJson);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException | RequestException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse updateFollowUp(String followUpJson) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getUpdateFollowUpAddress();
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.writeBody(followUpJson);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse deactivateFollowUp(String followUpId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getDeactivateFollowUpsAddress(followUpId);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (RequestException | IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse listFollowUps() throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getListFollowUpsAddress();
    try {
      JsonGETUtility jsonGETUtility = new JsonGETUtility(requestURL);
      String response = jsonGETUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (RequestException | IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse createFollowUpEvent(String followUpId, String eventJson) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getCreateFollowUpEventAddress(followUpId);
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, eventJson);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (RequestException | IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse removeFollowUpEvent(String eventId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getRemoveFollowUpEventAddress(eventId);
    try {
      JsonPUTRequestUtility jsonPUT = new JsonPUTRequestUtility(requestURL);
      jsonPUT.finish();
      return new GatewayResponse().buildSuccess();
    } catch (RequestException | IOException ex) {
      throw new ReadRequestException();
    }
  }
}
