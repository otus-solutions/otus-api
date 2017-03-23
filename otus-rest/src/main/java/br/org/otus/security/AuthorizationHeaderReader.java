package br.org.otus.security;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;

public class AuthorizationHeaderReader {

	public static String readToken(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new HttpResponseException(Validation.build());
		}

		return authorizationHeader.substring("Bearer".length()).trim();
	}
}
