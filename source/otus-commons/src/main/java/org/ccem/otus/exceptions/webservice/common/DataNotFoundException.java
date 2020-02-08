package org.ccem.otus.exceptions.webservice.common;

public class DataNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;
  private Object data;

  public DataNotFoundException(String message) {
    super(new Throwable(message));
  }

  public DataNotFoundException() {
  }

  public DataNotFoundException(Throwable cause) {
    super(cause);
  }

  public DataNotFoundException(Throwable cause, Object data) {
    super(cause);
    this.data = data;
  }


  public Object getData() {
    return data;
  }

}
