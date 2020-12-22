package br.org.otus.gateway.request;

public enum RequestTypeOptions {

  GET("GET"),
  PUT("PUT"),
  POST("POST"),
  DELETE("DELETE");

  private String name;

  RequestTypeOptions(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
