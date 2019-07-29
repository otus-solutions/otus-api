package br.org.otus.gateway.response;

public class RequestException extends RuntimeException {

    private int errorCode;

    public RequestException(int errorCode){
        this.errorCode = errorCode;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
