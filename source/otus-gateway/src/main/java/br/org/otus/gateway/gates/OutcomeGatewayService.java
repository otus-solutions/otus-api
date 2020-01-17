package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonPOSTUtility;
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
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, followUpJson);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException | RequestException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse deactivateFollowUp(String followUpId) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getDeactivateFollowUpsAddress(followUpId);
    try {
//      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, body);
//      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess();
    } catch (RequestException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse listFollowUps() throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getListFollowUpsAddress();
    try {
//      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL);
//      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess();
    } catch (RequestException ex) {
      throw new ReadRequestException();
    }
  }
}
