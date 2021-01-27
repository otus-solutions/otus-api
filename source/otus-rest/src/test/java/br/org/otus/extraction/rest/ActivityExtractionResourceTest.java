package br.org.otus.extraction.rest;

import br.org.otus.extraction.ActivityExtractionFacade;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Inject;

public class ActivityExtractionResourceTest {

  private static final String PIPELINE_NAME = "pipeline";

  @Inject
  private ActivityExtractionResource activityExtractionResource;
  @Mock
  private ActivityExtractionFacade extractionFacade;

  //@Test
  public void extractFromPipeline_method_should_call_createExtractionFromPipeline_method() {
    activityExtractionResource.extractJsonFromPipeline(PIPELINE_NAME);
    Mockito.verify(extractionFacade).createJsonExtractionFromPipeline(PIPELINE_NAME);
  }
}
