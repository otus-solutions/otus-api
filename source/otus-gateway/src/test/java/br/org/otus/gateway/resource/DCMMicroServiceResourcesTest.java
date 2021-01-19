package br.org.otus.gateway.resource;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.gateway.MicroservicesEnvironments;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DCMMicroServiceResources.class})
public class DCMMicroServiceResourcesTest extends MicroServiceResourcesTestParent {

  private DCMMicroServiceResources resources;

  @Before
  public void setUp() throws Exception {
    parentSetUp(MicroservicesEnvironments.DCM);
    resources = new DCMMicroServiceResources();
  }

  @Test
  public void getRetinographyImageAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/retinography");
    assertEquals(resources.getRetinographyImageAddress(), url);
  }

  @Test
  public void getUltrasoundImageAddress_method_should_return_expected_url() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/ultrasound");
    assertEquals(resources.getUltrasoundImageAddress(), url);
  }

}
