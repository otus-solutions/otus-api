package br.org.otus.response.info;

import javax.ws.rs.core.Response;

import br.org.otus.response.exception.ResponseInfo;

public class NotFound extends ResponseInfo {

  public NotFound() {
    super(Response.Status.NOT_FOUND, "Data Not Found");
  }

  public NotFound(String message) {
    super(Response.Status.NOT_FOUND, "Data Not Found: " + message);
  }

  public static ResponseInfo build() {
    return new NotFound();
  }

  public static ResponseInfo build(String message) {
    return new NotFound(message);
  }
}
