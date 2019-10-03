package org.ccem.otus.exceptions.webservice.validation;

public class ValidationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private Object data;

	public ValidationException(Throwable cause) {
		super(cause);
	}
	
	public ValidationException(Throwable cause, Object data) {
		super(cause);
		this.data = data;
	}
	
	public ValidationException(Object data) {
		this.data = data;
	}

	public ValidationException() {
	}

	public Object getData() {
		return data;
	}
	
}
