package br.org.otus.response.info;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.exception.ResponseInfo;

@RunWith(PowerMockRunner.class)
public class AuthorizationTest {

	private Authorization authorizationExpect;
	private ResponseInfo authorization;

	@Before
	public void setUp() {
		authorizationExpect = new Authorization();
		authorization = authorizationExpect.build();
	}

	@Test
	public void method_ResponseInfo_should_build_object_for_constructor() {
		assertEquals(authorizationExpect.MESSAGE, authorization.MESSAGE);

	}

}
