package br.org.otus.gateway;

public enum MicroservicesEnvironments {
  
  DBDISTRIBUTION("DBDISTRIBUTION_HOST", "DBDISTRIBUTION_PORT");

  private String host;
  private String port;

  MicroservicesEnvironments(String host, String port) {
    this.host = host;
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  public String getPort() {
    return port;
  }

}
