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
@PrepareForTest({ DBDistributionMicroServiceResources.class })
public class DBDistributionMicroServiceResourcesTest {

  private static final String HOST = "http://localhost:";
  private static final String PORT = "8081";
  private DBDistributionMicroServiceResources dbDistributionMicroServiceResources;
  private MicroservicesEnvironments microservicesEnvironments;
  private URL url;

  @Before
  public void setUp() throws Exception {
    microservicesEnvironments = MicroservicesEnvironments.DBDISTRIBUTION;
    mockStatic(System.class);
    when(System.getenv(microservicesEnvironments.getHost())).thenReturn(HOST);
    when(System.getenv(microservicesEnvironments.getPort())).thenReturn(PORT);

    dbDistributionMicroServiceResources = new DBDistributionMicroServiceResources();
  }

  @Test
  public void getFindVariableAddressMethod_should_return_variablesFindUrl() throws MalformedURLException {
    url = new URL("http://localhost:8081/api/findVariables");
    assertEquals(dbDistributionMicroServiceResources.getFindVariableAddress(), url);
  }

  @Test
  public void getDatabaseUploadAddressMethod_should_return_databaseUploadUrl() throws MalformedURLException {
    url = new URL("http://localhost:8081/api/upload/database");
    assertEquals(dbDistributionMicroServiceResources.getDatabaseUploadAddress(), url);
  }

  @Test
  public void getVariableTypeCorrelationUploadAddressMethod_should_return_correlationUploadUrl() throws MalformedURLException {
    url = new URL("http://localhost:8081/api/upload/variable-type-correlation");
    assertEquals(dbDistributionMicroServiceResources.getVariableTypeCorrelationUploadAddress(), url);
  }

}