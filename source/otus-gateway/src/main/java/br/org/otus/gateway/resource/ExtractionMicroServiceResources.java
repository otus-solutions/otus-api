package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class ExtractionMicroServiceResources extends MicroservicesResources {

  private static final String EXTRACTION_SUFFIX = "/extractions";
  private static final String ACTIVITY_EXTRACTION_RESOURCE = EXTRACTION_SUFFIX + "/activity";

  private static final String SURVEY_EXTRACTION_SUFFIX = "/survey";
  private static final String SURVEY_CSV_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/csv";
  private static final String SURVEY_JSON_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/json";

  public ExtractionMicroServiceResources() {
    super(MicroservicesEnvironments.EXTRACTION);
  }

  public URL getActivityExtractionCreateAddress() throws MalformedURLException {
    return new URL(getMainAddress() + ACTIVITY_EXTRACTION_RESOURCE);
  }

  public URL getActivityExtractionDeleteAddress(String surveyId, String activityId) throws MalformedURLException {
    return new URL(getMainAddress() + ACTIVITY_EXTRACTION_RESOURCE + "/" + surveyId + "/" + activityId);
  }

  public URL getCsvSurveyExtractionAddress(String surveyId) throws MalformedURLException {
    return new URL(getMainAddress() + SURVEY_CSV_EXTRACTION_RESOURCE + "/" + surveyId);
  }

  public URL getJsonSurveyExtractionAddress(String surveyId) throws MalformedURLException {
    return new URL(getMainAddress() + SURVEY_JSON_EXTRACTION_RESOURCE + "/" + surveyId);
  }
}
