package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.io.UnsupportedEncodingException;
import java.net.*;

public class ExtractionMicroServiceResources extends MicroservicesResources {

  private static final String EXTRACTION_SUFFIX = "/extractions";
  private static final String ACTIVITY_EXTRACTION_RESOURCE = EXTRACTION_SUFFIX + "/activity";

  private static final String SURVEY_EXTRACTION_SUFFIX = "/survey";
  private static final String CSV_SURVEY_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/csv";
  private static final String JSON_SURVEY_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/json";
  private static final String RSCRIPT_SURVEY_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/rscript";
  private static final String SURVEY_ACTIVITIES_IDS_WITH_EXTRACTION_RESOURCE = SURVEY_EXTRACTION_SUFFIX + "/get-survey-activities-ids";

  private static final String RSCRIPT_SUFFIX = "/rscript";
  private static final String RSCRIPT_CREATE_RESOURCE = RSCRIPT_SUFFIX + "/create";
  private static final String SPACE_0020 = "%20";
  private static final String REPLACE_SPACE = " ";

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

  public URL getRScriptCreationAddress() throws MalformedURLException {
    return new URL(getMainAddress() + RSCRIPT_CREATE_RESOURCE);
  }

  public URL getRScriptGetterAddress(String rscriptName) throws MalformedURLException, URISyntaxException {
    return new URI(getMainAddress() + RSCRIPT_SUFFIX + "/" + rscriptName.replace(REPLACE_SPACE, SPACE_0020)).toURL();
  }

  public URL getRScriptDeleteAddress(String rscriptName) throws MalformedURLException, URISyntaxException {
    return new URI(getMainAddress() + RSCRIPT_SUFFIX + "/" + rscriptName.replace(REPLACE_SPACE, SPACE_0020)).toURL();
  }

  public URL getRScriptJsonSurveyExtractionAddress() throws MalformedURLException {
    return new URL(getMainAddress() + RSCRIPT_SURVEY_EXTRACTION_RESOURCE);
  }
}
