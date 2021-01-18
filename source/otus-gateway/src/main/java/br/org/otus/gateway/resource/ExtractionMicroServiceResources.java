package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class ExtractionMicroServiceResources extends MicroservicesResources {

  private static final String PIPELINE_EXTRACTION_SUFFIX = "/pipeline/";
  private static final String PIPELINE_JSON_EXTRACTION_RESOURCE = PIPELINE_EXTRACTION_SUFFIX + "json/";
  private static final String PIPELINE_CSV_EXTRACTION_RESOURCE = PIPELINE_EXTRACTION_SUFFIX + "csv/";

  private static final String EXTRACTION_SUFFIX = "/extractions";
  private static final String ACTIVITY_EXTRACTION_SUFFIX = EXTRACTION_SUFFIX + "/activity";
  private static final String EXTRACTION_CREATE_RESOURCE = ACTIVITY_EXTRACTION_SUFFIX + "/create";
  private static final String EXTRACTION_DELETE_RESOURCE = ACTIVITY_EXTRACTION_SUFFIX + "/delete";

  public ExtractionMicroServiceResources() {
    super(MicroservicesEnvironments.EXTRACTION);
  }

  public URL getPipelineJsonExtractionAddress(String pipelineName) throws MalformedURLException {
    return new URL(getMainAddress() + PIPELINE_JSON_EXTRACTION_RESOURCE + pipelineName);
  }

  public URL getPipelineCsvExtractionAddress(String pipelineName) throws MalformedURLException {
    return new URL(getMainAddress() + PIPELINE_CSV_EXTRACTION_RESOURCE + pipelineName);
  }

  public URL getActivityExtractionCreateAddress() throws MalformedURLException {
    return new URL(getMainAddress() + EXTRACTION_CREATE_RESOURCE);
  }

  public URL getActivityExtractionDeleteAddress(String surveyId, String activityId) throws MalformedURLException {
    return new URL(getMainAddress() + EXTRACTION_DELETE_RESOURCE + "/" + surveyId + "/" + activityId);
  }
}
