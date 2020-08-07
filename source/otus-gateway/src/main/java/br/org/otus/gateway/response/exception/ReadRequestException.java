package br.org.otus.gateway.response.exception;

public class ReadRequestException extends RuntimeException {
  public ReadRequestException() {
  }

  public ReadRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
