package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MicroservicesResources.class})
public class MicroservicesResourcesTest {
  private static final String HOST = "http://localhost:";
  private static final String PORT = "8081";

  private MicroservicesResources microservicesResources;
  private MicroservicesEnvironments microservicesEnvironments;

  @Before
  public void setUp() throws Exception {
    microservicesEnvironments = MicroservicesEnvironments.DBDISTRIBUTION;
  }

  @Test
  public void test_should_check_if_constructor_is_filling_attributes() throws Exception {
    mockStatic(System.class);
    when(System.getenv(microservicesEnvironments.getHost())).thenReturn(HOST);
    when(System.getenv(microservicesEnvironments.getPort())).thenReturn(PORT);

    microservicesResources = new MicroservicesResources(microservicesEnvironments);
    assertEquals(microservicesResources.HOST, HOST);
    assertEquals(microservicesResources.PORT, PORT);
  }
}