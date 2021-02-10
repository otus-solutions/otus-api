package br.org.otus.gateway.response.exception;

import java.net.HttpURLConnection;

public class NotFoundRequestException extends RequestException {

  public NotFoundRequestException() {
    super(HttpURLConnection.HTTP_NOT_FOUND, "Not Found", null);
  }

}
