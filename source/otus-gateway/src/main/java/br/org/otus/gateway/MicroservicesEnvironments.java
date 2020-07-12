package br.org.otus.gateway;

public enum MicroservicesEnvironments {

  DBDISTRIBUTION("DBDISTRIBUTION_HOST", "DBDISTRIBUTION_PORT"),
  DCM("DCM_HOST", "DCM_PORT"),
  OUTCOMES("OUTCOMES_HOST", "OUTCOMES_PORT"),
  COMMUNICATION_SERVICE("COMMUNICATION_HOST", "COMMUNICATION_PORT"),
  REPORT("REPORT_HOST", "REPORT_PORT");

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
