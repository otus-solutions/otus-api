package br.org.otus.response.info;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.org.otus.response.exception.ResponseInfo;

public class AuthorizationTest {
	private Authorization authorizationExpect;
	private ResponseInfo authorization;

	@Test
	public void method_ResponseInfo_should_build_object_for_constructor() {
		authorizationExpect = new Authorization();
		authorization = authorizationExpect.build();
		assertEquals(authorizationExpect.MESSAGE, authorization.MESSAGE);
	}

}
