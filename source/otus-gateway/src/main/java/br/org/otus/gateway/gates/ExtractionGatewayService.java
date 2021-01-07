package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.*;
import br.org.otus.gateway.resource.ExtractionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;

import java.io.IOException;
import java.net.URL;

public class ExtractionGatewayService {

  public GatewayResponse getPipelineJsonExtraction(String pipelineName) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getPipelineJsonExtractionAddress(pipelineName);
    return getPipelineExtraction(requestURL);
  }

  public GatewayResponse getPipelineCsvJsonExtraction(String pipelineName) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getPipelineCsvExtractionAddress(pipelineName);
    return getPipelineExtraction(requestURL);
  }

  private GatewayResponse getPipelineExtraction(URL requestURL){
    try {
      String response = new JsonGETUtility(requestURL).finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException e) {
      throw new ReadRequestException();
    }
  }

  public void createActivityExtraction(String activityId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getActivityExtractionCreateAddress(activityId);
    sendActivityExtractionRequest(new JsonPOSTUtility(requestURL, ""));
  }

  public void updateActivityExtraction(String activityId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getActivityExtractionUpdateAddress(activityId);
    sendActivityExtractionRequest(new JsonPUTRequestUtility(requestURL));
  }

  public void deleteActivityExtraction(String activityId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getActivityExtractionDeleteAddress(activityId);
    sendActivityExtractionRequest(new JsonDELETEUtility(requestURL));
  }

  private void sendActivityExtractionRequest(JsonRequestUtility jsonRequestUtility){
    try {
      jsonRequestUtility.finish();
    } catch (IOException e) {
      throw new ReadRequestException();
    }
  }

}
