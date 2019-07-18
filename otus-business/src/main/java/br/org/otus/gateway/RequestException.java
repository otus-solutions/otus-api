package br.org.otus.gateway;

public class RequestException extends RuntimeException {

    private int errorCode;

    RequestException(int errorCode){
        this.errorCode = errorCode;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
