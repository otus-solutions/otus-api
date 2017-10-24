package br.org.otus.extraction;

public class ExtractionNotAuthorizedException extends Exception {

	private static final long serialVersionUID = -1904003541858843885L;

	public ExtractionNotAuthorizedException(String cause) {
		super(cause);
	}

}
