package br.org.otus.response.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResponseInfo {
  public Response.Status STATUS = Response.Status.INTERNAL_SERVER_ERROR;
  public String MESSAGE = "Internal Error";
  public Object CONTENT;

  public ResponseInfo(Response.Status status, String message) {
    this.STATUS = status;
    this.MESSAGE = message;
  }

  public ResponseInfo(Response.Status status, String message, Object o) {
    this.STATUS = status;
    this.MESSAGE = message;
    this.CONTENT = o;
  }

  public ResponseInfo() {
  }

  public Response toResponse() {
    return Response.status(STATUS)
      .entity(this)
      .type(MediaType.APPLICATION_JSON).
        build();
  }
}
