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

  public GatewayResponse createOutcome(String body) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getCreateOutcomeAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, body);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException | RequestException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse updateOutcome(String body) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getUpdateOutcomeAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, body);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException | RequestException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse listOutcomes(String body) throws MalformedURLException {
    URL requestURL = new OutcomesMicroServiceResources().getListOutcomesAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, body);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException | RequestException ex) {
      throw new ReadRequestException();
    }
  }

}
