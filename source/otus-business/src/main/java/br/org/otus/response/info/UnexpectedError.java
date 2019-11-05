package br.org.otus.response.info;

import br.org.otus.response.exception.ResponseInfo;

import javax.ws.rs.core.Response;

public class UnexpectedError extends ResponseInfo {

  public UnexpectedError() {
    super(Response.Status.INTERNAL_SERVER_ERROR, "Error");
  }

  public UnexpectedError(String message) {
    super(Response.Status.INTERNAL_SERVER_ERROR, "Error:  " + message);
  }

  public UnexpectedError(String message, Object object) {
    super(Response.Status.INTERNAL_SERVER_ERROR, "Error: " + message, object);
  }

  public static ResponseInfo build(){
    return new UnexpectedError();
  }

  public static ResponseInfo build(String message){
    return new UnexpectedError(message);
  }

  public static ResponseInfo build(String message, Object object){
    return new UnexpectedError(message, object);
  }

}
