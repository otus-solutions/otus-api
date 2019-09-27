package br.org.otus.gateway.gates;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ejb.Stateless;

import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.resource.DCMMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;

@Stateless
public class DCMGatewayService {

  private DCMMicroServiceResources resource;

  public DCMGatewayService() {
    this.resource = new DCMMicroServiceResources();
  }

  public GatewayResponse findRetinography(String body) throws MalformedURLException {
    URL requestURL = this.resource.getRetinographyImageAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, body);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException | RequestException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse findUltrasound(String body) throws MalformedURLException {
    URL requestURL = this.resource.getUltrasoundImageAddress();
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(requestURL, body);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException | RequestException ex) {
      throw new ReadRequestException();
    }
  }

}
