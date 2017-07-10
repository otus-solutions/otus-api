package br.org.otus.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;

@RunWith(PowerMockRunner.class)
public class AuthorizationHeaderReaderTest {

	HttpServletRequest request;

	private String token;
	private String authorizationHeaderExpected;
	@Before
	public void setUp() {
		token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
		authorizationHeaderExpected = token.substring("bearer".length()).trim();
	}

	@Test
	public void method_readToken_should_read_authorizationHeader() {
		assertEquals(authorizationHeaderExpected, AuthorizationHeaderReader.readToken(token));
	}

	@Test(expected = HttpResponseException.class)
	public void method_readToken_should_throw_HttpResponseException() {
		when(AuthorizationHeaderReader.readToken(anyString())).thenThrow(new HttpResponseException(Validation.build()));
	}

}
