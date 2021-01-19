package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.URL;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

public abstract class MicroServiceResourcesTestParent {

  protected static final String HOST = "localhost";
  protected static final String PORT = "8081";

  protected MicroservicesEnvironments microservicesEnvironments;
  protected URL url;

  protected void parentSetUp(MicroservicesEnvironments microservicesEnvironments) {
    this.microservicesEnvironments = microservicesEnvironments;
    mockStatic(System.class);
    when(System.getenv(microservicesEnvironments.getHost())).thenReturn(HOST);
    when(System.getenv(microservicesEnvironments.getPort())).thenReturn(PORT);
  }

}
