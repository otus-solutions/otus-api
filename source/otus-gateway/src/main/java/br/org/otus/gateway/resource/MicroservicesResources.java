package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

public class MicroservicesResources {

  protected String HOST;
  protected String PORT;
  private MicroservicesEnvironments microservicesEnvironments;

  public MicroservicesResources(MicroservicesEnvironments microservicesEnvironments) {
    this.microservicesEnvironments = microservicesEnvironments;

    HOST = System.getenv(microservicesEnvironments.getHost());
    PORT = System.getenv(microservicesEnvironments.getPort());
  }

  protected String getMainAddress(){
    return "http://" + this.HOST + ":" + this.PORT;
  }

}
