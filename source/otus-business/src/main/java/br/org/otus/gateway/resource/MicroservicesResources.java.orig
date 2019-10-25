package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

public class MicroservicesResources {
  protected static String PROTOCOL = "HTTP";;
  protected static String HOST;
  public static int PORT;
  private MicroservicesEnvironments microservicesEnvironments;

  public MicroservicesResources(MicroservicesEnvironments microservicesEnvironments) {
    this.microservicesEnvironments = microservicesEnvironments;
    readAddress();
  }

  private void readAddress() {
    HOST = System.getenv(microservicesEnvironments.getHost());
    PORT = Integer.parseInt(System.getenv(microservicesEnvironments.getPort()));
  }

}
