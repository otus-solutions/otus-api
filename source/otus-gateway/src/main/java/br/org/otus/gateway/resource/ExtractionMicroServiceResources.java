package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class ExtractionMicroServiceResources extends MicroservicesResources {

  private static final String PIPELINE_EXTRACTION_RESOURCE = "/pipeline/";

  private static final String EXTRACTION_SUFFIX = "/extraction/";
  private static final String EXTRACTION_CREATE_RESOURCE = EXTRACTION_SUFFIX + "create/";
  private static final String EXTRACTION_UPDATE_RESOURCE = EXTRACTION_SUFFIX + "update/";
  private static final String EXTRACTION_DELETE_RESOURCE = EXTRACTION_SUFFIX + "delete/";

  public ExtractionMicroServiceResources() {
    super(MicroservicesEnvironments.EXTRACTION);
  }

  public URL getPipelineExtractionAddress(String pipelineName) throws MalformedURLException {
    return new URL(getMainAddress() + PIPELINE_EXTRACTION_RESOURCE + pipelineName);
  }

  public URL getActivityExtractionCreateAddress(String activityId) throws MalformedURLException {
    return new URL(getMainAddress() + EXTRACTION_CREATE_RESOURCE + activityId);
  }

  public URL getActivityExtractionUpdateAddress(String activityId) throws MalformedURLException {
    return new URL(getMainAddress() + EXTRACTION_UPDATE_RESOURCE + activityId);
  }

  public URL getActivityExtractionDeleteAddress(String activityId) throws MalformedURLException {
    return new URL(getMainAddress() + EXTRACTION_DELETE_RESOURCE + activityId);
  }
}
