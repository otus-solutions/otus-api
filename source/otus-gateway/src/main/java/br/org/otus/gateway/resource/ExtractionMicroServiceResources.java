package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class ExtractionMicroServiceResources extends MicroservicesResources {

  private static final String EXTRACTION_SUFFIX = "/extractions";
  private static final String ACTIVITY_EXTRACTION_RESOURCE = EXTRACTION_SUFFIX + "/activity";

  private static final String SURVEY_EXTRACTION_SUFFIX = "/survey";
  private static final String CSV_SURVEY_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/csv";
  private static final String JSON_SURVEY_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/json";
  private static final String RSCRIPT_SURVEY_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/rscript";
  private static final String SURVEY_ACTIVITIES_IDS_WITH_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/get-survey-activities-ids";

  public ExtractionMicroServiceResources() {
    super(MicroservicesEnvironments.EXTRACTION);
  }

  public URL getActivityExtractionCreateAddress() throws MalformedURLException {
    return new URL(getMainAddress() + ACTIVITY_EXTRACTION_RESOURCE);
  }

  public URL getActivityExtractionDeleteAddress(String surveyId, String activityId) throws MalformedURLException {
    return new URL(getMainAddress() + ACTIVITY_EXTRACTION_RESOURCE + "/" + surveyId + "/" + activityId);
  }

  public URL getSurveyActivityIdsWithExtractionAddress(String surveyId) throws MalformedURLException {
    return new URL(getMainAddress() + SURVEY_ACTIVITIES_IDS_WITH_EXTRACTION_RESOURCE + "/" + surveyId);
  }

  public URL getCsvSurveyExtractionAddress(String surveyId) throws MalformedURLException {
    return new URL(getMainAddress() + CSV_SURVEY_EXTRACTION_RESOURCE + "/" + surveyId);
  }

  public URL getJsonSurveyExtractionAddress(String surveyId) throws MalformedURLException {
    return new URL(getMainAddress() + JSON_SURVEY_EXTRACTION_RESOURCE + "/" + surveyId);
  }

  public URL getRScriptJsonSurveyExtractionAddress() throws MalformedURLException {
    return new URL(getMainAddress() + RSCRIPT_SURVEY_EXTRACTION_RESOURCE);
  }
}
