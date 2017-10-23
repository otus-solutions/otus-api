package br.org.otus.extraction;

public class AuthorizationFailedException extends Exception {

	private static final long serialVersionUID = -1904003541858843885L;

	public AuthorizationFailedException(String cause) {
		super(cause);
	}

}
