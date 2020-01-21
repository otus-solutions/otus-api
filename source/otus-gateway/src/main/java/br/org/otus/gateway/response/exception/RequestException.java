package br.org.otus.gateway.response.exception;

public class RequestException extends RuntimeException {

  private int errorCode;
  private String errorMessage;
  private Object errorContent;

  public RequestException(int errorCode) {
    this.errorCode = errorCode;
  }

  public RequestException(int status, String errorMessage, Object errorContent) {
    this.errorCode = status;
    this.errorMessage = errorMessage;
    this.errorContent = errorContent;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public Object getErrorContent() {
    return errorContent;
  }
}
