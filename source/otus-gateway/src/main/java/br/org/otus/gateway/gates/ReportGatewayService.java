package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.resource.ReportMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ReportGatewayService {
  public GatewayResponse getReportTemplate(String dataJson) throws MalformedURLException {
    URL requestURL = new ReportMicroServiceResources().getReportTemplate();
    try {
      JsonPOSTUtility jsonPOSTUtility = new JsonPOSTUtility(requestURL, dataJson);
      String response = jsonPOSTUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException ex) {
      throw new ReadRequestException();
    }
  }
}
