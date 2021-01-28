package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.*;
import br.org.otus.gateway.resource.ExtractionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;

import java.io.IOException;
import java.net.URL;

public class ExtractionGatewayService {

  public void createOrUpdateActivityExtraction(String activityExtractionJson) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getActivityExtractionCreateAddress();
    sendRequest(new JsonPUTRequestUtility(requestURL, activityExtractionJson));
  }

  public void deleteActivityExtraction(String surveyId, String activityId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getActivityExtractionDeleteAddress(surveyId, activityId);
    sendRequest(new JsonDELETEUtility(requestURL));
  }

  public GatewayResponse getSurveyActivityIdsWithExtraction(String surveyId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getSurveyActivityIdsWithExtractionAddress(surveyId);
    try {
      String response = new JsonGETUtility(requestURL).finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException e) {
      throw new ReadRequestException();
    }
  }

  public GatewayResponse getCsvSurveyExtraction(String surveyId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getCsvSurveyExtractionAddress(surveyId);
    return getSurveyExtraction(requestURL);
  }

  public GatewayResponse getJsonSurveyExtraction(String surveyId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getJsonSurveyExtractionAddress(surveyId);
    return getSurveyExtraction(requestURL);
  }

  public GatewayResponse getRscriptSurveyExtraction(String rscriptSurveyExtractionJson) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getRScriptJsonSurveyExtractionAddress();
    try {
      String response = new JsonPOSTUtility(requestURL, rscriptSurveyExtractionJson).finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (IOException e) {
      throw new ReadRequestException();
    }
  }


  private void sendRequest(JsonRequestUtility jsonRequestUtility){
    try {
      jsonRequestUtility.finish();
    } catch (IOException e) {
      throw new ReadRequestException();
    }
  }

  private GatewayResponse getSurveyExtraction(URL requestURL){
    try {
      String response = new JsonGETUtility(requestURL).finish();
      return new GatewayResponse().buildSuccess(response);
    } catch (Exception e) {
      throw new ReadRequestException(e.getMessage(), e.getCause());
    }
  }
}
