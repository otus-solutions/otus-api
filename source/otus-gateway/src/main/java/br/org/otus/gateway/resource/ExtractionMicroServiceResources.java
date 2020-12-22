package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class ExtractionMicroServiceResources extends MicroservicesResources {

  private static final String ACTIVITY_EXTRACTION_RESOURCE = "/activity/";
  private static final String ACTIVITIES_EXTRACTION_RESOURCE = "/activities/";

  public ExtractionMicroServiceResources() {
    super(MicroservicesEnvironments.EXTRACTION);
  }

  public URL getActivityExtractionAddress(String activityId) throws MalformedURLException {
    return new URL(getMainAddress() + ACTIVITY_EXTRACTION_RESOURCE + activityId);
  }

  public URL getActivitiesExtractionAddress(String pipelineName) throws MalformedURLException {
    return new URL(getMainAddress() + ACTIVITIES_EXTRACTION_RESOURCE + pipelineName);
  }
}
