package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ExtractionMicroServiceResources.class})
public class ExtractionMicroServiceResourcesTest extends MicroServiceResourcesTestParent {

  private static final String PIPELINE_NAME = "pipelineName";

  private ExtractionMicroServiceResources resources;

  @Before
  public void setUp() throws Exception {
    parentSetUp(MicroservicesEnvironments.EXTRACTION);
    resources = new ExtractionMicroServiceResources();
  }

  @Test
  public void getCreateOutcomeAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://" + HOST + ":" + PORT + "/pipeline/" + PIPELINE_NAME);
    Assert.assertEquals(url, resources.getPipelineExtractionAddress(PIPELINE_NAME));
  }

}
