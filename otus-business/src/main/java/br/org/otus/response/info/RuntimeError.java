package br.org.otus.response.info;

import br.org.otus.response.exception.ResponseInfo;

import javax.ws.rs.core.Response;

public class RuntimeError extends ResponseInfo {

  public RuntimeError() {
    super(Response.Status.INTERNAL_SERVER_ERROR, "Error");
  }

  public RuntimeError(String message) {
    super(Response.Status.INTERNAL_SERVER_ERROR, "Error:  " + message);
  }

  public RuntimeError(String message, Object object) {
    super(Response.Status.INTERNAL_SERVER_ERROR, "Error: " + message, object);
  }

  public static ResponseInfo build(){
    return new RuntimeError();
  }

  public static ResponseInfo build(String message){
    return new RuntimeError(message);
  }

  public static ResponseInfo build(String message, Object object){
    return new RuntimeError(message, object);
  }

}
