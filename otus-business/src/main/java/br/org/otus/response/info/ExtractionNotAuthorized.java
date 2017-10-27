package br.org.otus.response.info;

import javax.ws.rs.core.Response;

import br.org.otus.response.exception.ResponseInfo;

public class ExtractionNotAuthorized extends ResponseInfo {

	public ExtractionNotAuthorized() {
		super(Response.Status.UNAUTHORIZED, "Extraction not authorized");
	}

	public static ResponseInfo build() {
		return new ExtractionNotAuthorized();
	}

}
