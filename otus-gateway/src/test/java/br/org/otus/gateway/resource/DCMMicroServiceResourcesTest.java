package br.org.otus.gateway.resource;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.gateway.MicroservicesEnvironments;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DCMMicroServiceResources.class })
public class DCMMicroServiceResourcesTest {

  private static final String HOST = "http://localhost:";
  private static final String PORT = "8081";
  private DCMMicroServiceResources resources;
  private MicroservicesEnvironments microservicesEnvironments;
  private URL url;

  @Before
  public void setUp() throws Exception {
    microservicesEnvironments = MicroservicesEnvironments.DBDISTRIBUTION;
    mockStatic(System.class);
    when(System.getenv(microservicesEnvironments.getHost())).thenReturn(HOST);
    when(System.getenv(microservicesEnvironments.getPort())).thenReturn(PORT);

    resources = new DCMMicroServiceResources();
  }

  @Test
  public void getRetinographyImageAddress_method_should_return_correlationUploadUrl() throws MalformedURLException {
    url = new URL("http://localhost:8081/api/retinography");
    assertEquals(resources.getRetinographyImageAddress(), url);
  }

  @Test
  public void getUltrasoundImageAddress_method_should_return_correlationUploadUrl() throws MalformedURLException {
    url = new URL("http://localhost:8081/api/ultrasound");
    assertEquals(resources.getUltrasoundImageAddress(), url);
  }

}
