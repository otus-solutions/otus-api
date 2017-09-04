package br.org.otus.security.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.security.dtos.AuthenticationDto;
import br.org.otus.security.dtos.ProjectAuthenticationDto;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;
import br.org.otus.security.services.SecurityService;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFacadeTest {
	private static final String REQUEST_ADDRESS = "http://api.domain.dev.ccem.ufrgs.br:8080";
	private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";
	@InjectMocks
	private SecurityFacade securityFacade;
	@Mock
	private SecurityService securityService;
	@Mock
	private AuthenticationDto authenticationDto;
	@Mock
	private UserSecurityAuthorizationDto userSecurityAuthorizationDto;
	@Mock
	private ProjectAuthenticationDto projectAuthenticationDto;

	@Before
	public void setUp() {
	}

	@Test
	public void method_UserAuthentication_should_return_userSecurityAuthorizationDto()
			throws TokenException, AuthenticationException {
		Mockito.when(securityService.authenticate(authenticationDto)).thenReturn(userSecurityAuthorizationDto);
		assertTrue(securityFacade.userAuthentication(authenticationDto,
				REQUEST_ADDRESS) instanceof UserSecurityAuthorizationDto);
		verify(authenticationDto).setRequestAddress(REQUEST_ADDRESS);
	}

	@Test(expected = HttpResponseException.class)
	public void method_UserAuthentication_should_capture_AuthenticationException()
			throws TokenException, AuthenticationException {
		when(securityService.authenticate(authenticationDto)).thenThrow(AuthenticationException.class);
		securityFacade.userAuthentication(authenticationDto, REQUEST_ADDRESS);
		verify(authenticationDto).setRequestAddress(REQUEST_ADDRESS);
	}

	@Test(expected = HttpResponseException.class)
	public void method_UserAuthentication_should_capture_TokenException()
			throws TokenException, AuthenticationException {
		when(securityService.authenticate(authenticationDto)).thenThrow(TokenException.class);
		securityFacade.userAuthentication(authenticationDto, REQUEST_ADDRESS);
		verify(authenticationDto).setRequestAddress(REQUEST_ADDRESS);
	}

	@Test
	public void method_ProjectAuthentication_should_return_projectAuthentication()
			throws TokenException, AuthenticationException {
		when(securityService.projectAuthenticate(projectAuthenticationDto)).thenReturn(TOKEN);
		assertEquals(TOKEN, securityFacade.projectAuthentication(projectAuthenticationDto, REQUEST_ADDRESS));
		verify(projectAuthenticationDto).setRequestAddress(REQUEST_ADDRESS);
	}

	@Test(expected = HttpResponseException.class)
	public void method_ProjectAuthentication_should_capture_AuthenticationException()
			throws TokenException, AuthenticationException {
		when(securityService.projectAuthenticate(projectAuthenticationDto)).thenThrow(AuthenticationException.class);
		assertEquals(TOKEN, securityFacade.projectAuthentication(projectAuthenticationDto, REQUEST_ADDRESS));
		verify(projectAuthenticationDto).setRequestAddress(REQUEST_ADDRESS);
	}

	@Test(expected = HttpResponseException.class)
	public void method_ProjectAuthentication_should_capture_TokenException()
			throws TokenException, AuthenticationException {
		when(securityService.projectAuthenticate(projectAuthenticationDto)).thenThrow(TokenException.class);
		assertEquals(TOKEN, securityFacade.projectAuthentication(projectAuthenticationDto, REQUEST_ADDRESS));
		verify(projectAuthenticationDto).setRequestAddress(REQUEST_ADDRESS);
	}

	@Test
	public void method_Invalidate_evocate_invalidate_of_securityService() {
		securityFacade.invalidate(TOKEN);
		verify(securityService).invalidate(TOKEN);

	}

}
