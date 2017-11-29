package br.org.otus.security.dtos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProjectAuthenticationDtoTest {
	private static final String USER = "OtusUser";
	private static final String ACCESS_TOKEN = "347bcf7e-dcb2-4768-82eb-ee95d893f4c0";
	private static final String REQUEST_ADDRESS = "http://api.domain.dev.ccem.ufrgs.br:8080";
	@Spy
	ProjectAuthenticationDto projectAuthenticationDto;

	@Test
	public void method_isValid_isTrue_when_atributes_not_empty() {
		projectAuthenticationDto.user = USER;
		projectAuthenticationDto.accessToken = ACCESS_TOKEN;
		projectAuthenticationDto.setRequestAddress(REQUEST_ADDRESS);
		assertTrue(projectAuthenticationDto.isValid());
	}

	@Test
	public void method_isValid_isFalse_when_user_is_empty() {
		projectAuthenticationDto.user = "";
		projectAuthenticationDto.accessToken = ACCESS_TOKEN;
		projectAuthenticationDto.setRequestAddress(REQUEST_ADDRESS);
		assertFalse(projectAuthenticationDto.isValid());
	}

	@Test
	public void method_isValid_isFalse_when_accessToken_is_empty() {
		projectAuthenticationDto.user = USER;
		projectAuthenticationDto.accessToken = "";
		projectAuthenticationDto.setRequestAddress(REQUEST_ADDRESS);
		assertFalse(projectAuthenticationDto.isValid());
	}

	@Test
	public void method_isValid_isFalse_when_requestAddress_is_empty() {
		projectAuthenticationDto.user = USER;
		projectAuthenticationDto.accessToken = ACCESS_TOKEN;
		assertFalse(projectAuthenticationDto.isValid());
	}

	@Test
	public void method_buildClaimSet() {
		projectAuthenticationDto.user = USER;
		assertEquals(USER, projectAuthenticationDto.buildClaimSet().getIssuer());

	}

}
