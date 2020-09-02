package org.ccem.otus.exceptions.webservice.security;

public class TokenException extends Exception {
  public TokenException(Throwable cause) {
    super(cause);
  }

  public TokenException() {
  }

  public TokenException(String message) {
    super(message);
  }
}
