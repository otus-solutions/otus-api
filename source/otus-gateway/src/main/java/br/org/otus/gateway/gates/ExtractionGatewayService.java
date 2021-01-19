package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.*;
import br.org.otus.gateway.resource.ExtractionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;

import java.io.IOException;
import java.net.URL;

public class ExtractionGatewayService {

  public GatewayResponse getPipelineJsonExtraction(String pipelineJson) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getPipelineJsonExtractionAddress();
    return getPipelineExtraction(requestURL, pipelineJson);
  }

  public GatewayResponse getPipelineCsvJsonExtraction(String pipelineJson) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getPipelineCsvExtractionAddress();
    return getPipelineExtraction(requestURL, pipelineJson);
  }

  private GatewayResponse getPipelineExtraction(URL requestURL, String body){
    try {
      String response = new JsonPOSTUtility(requestURL, body).finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException e) {
      throw new ReadRequestException();
    }
  }

  public void createOrUpdateActivityExtraction(String activityExtractionJson) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getActivityExtractionCreateAddress();
    sendActivityExtractionRequest(new JsonPUTRequestUtility(requestURL, activityExtractionJson));
  }

  public void deleteActivityExtraction(String surveyId, String activityId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getActivityExtractionDeleteAddress(surveyId, activityId);
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
