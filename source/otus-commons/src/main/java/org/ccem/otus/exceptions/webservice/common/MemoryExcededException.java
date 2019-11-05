package org.ccem.otus.exceptions.webservice.common;

public class MemoryExcededException extends Exception {
	
	private static final long serialVersionUID = -314008716172624703L;
	
	public MemoryExcededException(String message) {
		super(new Throwable(message));		
	}
}
