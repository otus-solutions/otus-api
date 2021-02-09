package br.org.otus.gateway.gates;

import br.org.otus.gateway.request.*;
import br.org.otus.gateway.resource.ExtractionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;

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
    return sendRequestAndGetResponse(new JsonGETUtility(requestURL));
  }

  public GatewayResponse getCsvSurveyExtraction(String surveyId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getCsvSurveyExtractionAddress(surveyId);
    return sendRequestAndGetResponse(new JsonGETUtility(requestURL));
  }

  public GatewayResponse getJsonSurveyExtraction(String surveyId) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getJsonSurveyExtractionAddress(surveyId);
    return sendRequestAndGetResponse(new JsonGETUtility(requestURL));
  }

  public void createOrUpdateRscript(String rscriptJson) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getRScriptCreationAddress();
    sendRequest(new JsonPUTRequestUtility(requestURL, rscriptJson));
  }

  public GatewayResponse getRscript(String rscriptName) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getRScriptGetterAddress(rscriptName);
    return sendRequestAndGetResponse(new JsonGETUtility(requestURL));
  }

  public void deleteRscript(String rscriptName) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getRScriptDeleteAddress(rscriptName);
    sendRequest(new JsonDELETEUtility(requestURL));
  }

  public GatewayResponse getRscriptSurveyExtraction(String rscriptSurveyExtractionJson) throws IOException {
    URL requestURL = new ExtractionMicroServiceResources().getRScriptJsonSurveyExtractionAddress();
    return sendRequestAndGetResponse(new JsonPOSTUtility(requestURL, rscriptSurveyExtractionJson));
  }


  private void sendRequest(JsonRequestUtility jsonRequestUtility){
    try {
      jsonRequestUtility.finish();
    }
    catch (RequestException e){
      throw new ReadRequestException(e.getErrorMessage(), e.getCause());
    }
    catch (Exception e) {
      throw new ReadRequestException(e.getMessage(), e.getCause());
    }
  }

  private GatewayResponse sendRequestAndGetResponse(JsonRequestUtility jsonRequestUtility){
    try {
      String response = jsonRequestUtility.finish();
      return new GatewayResponse().buildSuccess(response);
    }
    catch (RequestException e){
      throw new ReadRequestException(e.getErrorMessage(), e.getCause());
    }
    catch (Exception e) {
      throw new ReadRequestException(e.getMessage(), e.getCause());
    }
  }
}
