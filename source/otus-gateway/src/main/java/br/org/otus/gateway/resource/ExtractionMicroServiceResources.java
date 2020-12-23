package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class ExtractionMicroServiceResources extends MicroservicesResources {

  private static final String PIPELINE_EXTRACTION_RESOURCE = "/pipeline/";

  public ExtractionMicroServiceResources() {
    super(MicroservicesEnvironments.EXTRACTION);
  }

  public URL getPipelineExtractionAddress(String pipelineName) throws MalformedURLException {
    return new URL(getMainAddress() + PIPELINE_EXTRACTION_RESOURCE + pipelineName);
  }
}
