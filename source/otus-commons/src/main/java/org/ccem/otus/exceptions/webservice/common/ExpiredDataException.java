package org.ccem.otus.exceptions.webservice.common;

public class ExpiredDataException extends Exception {

  public ExpiredDataException(){
    super();
  }

  public ExpiredDataException(String message){
    super(message);
  }

  public ExpiredDataException(Throwable cause){
    super(cause);
  }
}
