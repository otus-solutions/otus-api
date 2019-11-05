package br.org.otus.response.info;

import br.org.otus.response.exception.ResponseInfo;

import javax.ws.rs.core.Response;

public class Validation extends ResponseInfo{

    public Validation() {
        super(Response.Status.BAD_REQUEST, "Data Validation Fail");
    }
    
    public Validation(String message) {
    	super(Response.Status.BAD_REQUEST, "Data Validation Fail: " + message);
    }
 
    public Validation(String message, Object object) {
        super(Response.Status.BAD_REQUEST, "Data Validation Fail: " + message, object);
    }

    public static ResponseInfo build(){
        return new Validation();
    }
    
    public static ResponseInfo build(String message){
    	return new Validation(message);
    }
    
    public static ResponseInfo build(String message, Object object){
        return new Validation(message, object);
    }

}
