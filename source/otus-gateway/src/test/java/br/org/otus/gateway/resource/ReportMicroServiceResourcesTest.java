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

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ReportMicroServiceResources.class})
public class ReportMicroServiceResourcesTest extends MicroServiceResourcesTestParent {

  private ReportMicroServiceResources resources;

  @Before
  public void setUp() throws Exception {
    parentSetUp(MicroservicesEnvironments.REPORT);
    resources = new ReportMicroServiceResources();
  }

  @Test
  public void getReportTemplate_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/report");
    Assert.assertEquals(resources.getReportTemplate(), url);
  }

}
