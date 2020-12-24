package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.JsonGETUtility;
import br.org.otus.gateway.resource.ExtractionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ExtractionGatewayService {

  public GatewayResponse getPipelineExtraction(String pipelineName) throws MalformedURLException {
    URL requestURL = new ExtractionMicroServiceResources().getPipelineExtractionAddress(pipelineName);
    try {
      JsonGETUtility jsonGET = new JsonGETUtility(requestURL);
      String response = jsonGET.finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException e) {
      throw new ReadRequestException();
    }
  }

}
