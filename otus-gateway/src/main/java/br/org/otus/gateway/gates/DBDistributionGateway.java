package br.org.otus.gateway.gates;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.request.MultipartPOSTUtility;
import br.org.otus.gateway.resource.DBDistributionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;

public class DBDistributionGateway {

  public GatewayResponse find(String body, URL url) throws MalformedURLException {
    try {
      JsonPOSTUtility jsonPOST = new JsonPOSTUtility(url, body);
      String response = jsonPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException | RequestException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse uploadDatabase(File databaseFile) throws MalformedURLException {
    URL requestURL = new DBDistributionMicroServiceResources().getDatabaseUploadAddress();
    try {
      MultipartPOSTUtility multipartPOST = new MultipartPOSTUtility(requestURL);
      multipartPOST.addFilePart("databaseJson", databaseFile);
      String response = multipartPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse uploadVariableTypeCorrelation(File variableCorrelation) throws MalformedURLException {
    URL requestURL = new DBDistributionMicroServiceResources().getVariableTypeCorrelationUploadAddress();
    try {
      MultipartPOSTUtility multipartPOST = new MultipartPOSTUtility(requestURL);
      multipartPOST.addFilePart("variableTypeCorrelationJson", variableCorrelation);
      String response = multipartPOST.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }

}
