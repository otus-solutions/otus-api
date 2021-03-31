package br.org.otus.gateway.response.exception;

public class NotFoundRequestException extends RequestException {

  private static final int STATUS = java.net.HttpURLConnection.HTTP_NOT_FOUND;

  public NotFoundRequestException() {
    super(STATUS, "Not Found", null);
  }

  public NotFoundRequestException(String errorMessage, Object errorContent) {
    super(STATUS, errorMessage, errorContent);
  }
}
