package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class ExtractionMicroServiceResources extends MicroservicesResources {

  private static final String PIPELINE_EXTRACTION_SUFFIX = "/pipeline";
  private static final String PIPELINE_JSON_EXTRACTION_RESOURCE = PIPELINE_EXTRACTION_SUFFIX + "/json";
  private static final String PIPELINE_CSV_EXTRACTION_RESOURCE = PIPELINE_EXTRACTION_SUFFIX + "/csv";

  private static final String EXTRACTION_SUFFIX = "/extractions";
  private static final String ACTIVITY_EXTRACTION_RESOURCE = EXTRACTION_SUFFIX + "/activity";

  public ExtractionMicroServiceResources() {
    super(MicroservicesEnvironments.EXTRACTION);
  }

  public URL getPipelineJsonExtractionAddress() throws MalformedURLException {
    return new URL(getMainAddress() + PIPELINE_JSON_EXTRACTION_RESOURCE);
  }

  public URL getPipelineCsvExtractionAddress() throws MalformedURLException {
    return new URL(getMainAddress() + PIPELINE_CSV_EXTRACTION_RESOURCE);
  }

  public URL getActivityExtractionCreateAddress() throws MalformedURLException {
    return new URL(getMainAddress() + ACTIVITY_EXTRACTION_RESOURCE);
  }

  public URL getActivityExtractionDeleteAddress(String surveyId, String activityId) throws MalformedURLException {
    return new URL(getMainAddress() + ACTIVITY_EXTRACTION_RESOURCE + "/" + surveyId + "/" + activityId);
  }
}
