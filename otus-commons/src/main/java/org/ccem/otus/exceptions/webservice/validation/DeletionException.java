package org.ccem.otus.exceptions.webservice.validation;

public class DeletionException extends Exception {

  private static final long serialVersionUID = 1L;
  private Object data;

  public DeletionException(Throwable cause) {
    super(cause);
  }

  public DeletionException(Throwable cause, Object data) {
    super(cause);
    this.data = data;
  }

  public DeletionException(Object data) {
    this.data = data;
  }

  public DeletionException() {
  }

  public Object getData() {
    return data;
  }

}
