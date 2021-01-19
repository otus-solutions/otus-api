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
@PrepareForTest({DBDistributionMicroServiceResources.class})
public class DBDistributionMicroServiceResourcesTest extends MicroServiceResourcesTestParent {

  private DBDistributionMicroServiceResources dbDistributionMicroServiceResources;

  @Before
  public void setUp() throws Exception {
    parentSetUp(MicroservicesEnvironments.DBDISTRIBUTION);
    dbDistributionMicroServiceResources = new DBDistributionMicroServiceResources();
  }

  @Test
  public void getFindVariableAddressMethod_should_return_variablesFindUrl() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/findVariables");
    assertEquals(dbDistributionMicroServiceResources.getFindVariableAddress(), url);
  }

  @Test
  public void getDatabaseUploadAddressMethod_should_return_databaseUploadUrl() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/upload/database");
    assertEquals(dbDistributionMicroServiceResources.getDatabaseUploadAddress(), url);
  }

  @Test
  public void getVariableTypeCorrelationUploadAddressMethod_should_return_correlationUploadUrl() throws MalformedURLException {
    url = new URL("http://"+HOST+":"+PORT+"/api/upload/variable-type-correlation");
    assertEquals(dbDistributionMicroServiceResources.getVariableTypeCorrelationUploadAddress(), url);
  }

}