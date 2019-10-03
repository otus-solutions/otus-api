package br.org.otus.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;

@RunWith(PowerMockRunner.class)
public class AuthorizationHeaderReaderTest {
	private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
	private String authorizationHeaderExpected;

	@Test
	public void method_readToken_should_read_authorizationHeader() {
		authorizationHeaderExpected = TOKEN.substring("bearer".length()).trim();
		assertEquals(authorizationHeaderExpected, AuthorizationHeaderReader.readToken(TOKEN));
	}

	@Test(expected = HttpResponseException.class)
	public void method_readToken_should_throw_HttpResponseException() {
		Mockito.when(AuthorizationHeaderReader.readToken(anyString()))
				.thenThrow(new HttpResponseException(Validation.build()));
	}
}
