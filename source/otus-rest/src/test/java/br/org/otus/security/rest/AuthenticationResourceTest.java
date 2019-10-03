package br.org.otus.security.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.servlet.http.HttpServletRequest;

import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.nimbusds.jose.JOSEException;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.rest.Response;
import br.org.otus.security.EncryptorResources;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.AuthenticationDto;
import br.org.otus.security.dtos.ProjectAuthenticationDto;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EncryptorResources.class)
public class AuthenticationResourceTest {
	private static final String AUTHENTICATION_DTO_EMAIL = "otus@otus.com";
	private static final String HTTPSERVLET_REQUEST_IP = "143.54.220.57";
	private static final String ENCRYPT_IRREVERSIBLE = "123";
	private static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZ"
			+ "lcnJlaXJhQGdtYWlsLmNvbSJ9.6lNpZm4IhjINsUsWi5paX15dD7gPqlvapilx5Tf90NE";

	@InjectMocks
	private AuthenticationResource authenticationResource;
	@Mock
	private SecurityFacade securityFacade;
	@Mock
	private HttpServletRequest request;
	@InjectMocks
	private UserSecurityAuthorizationDto userSecurityAuthorizationDto;
	private AuthenticationDto authenticationDto;
	private Response response;
	private ProjectAuthenticationDto projectAuthenticationDto;

	@Before
	public void setUp() throws Exception {
		mockStatic(EncryptorResources.class);
		authenticationDto = new AuthenticationDto();
		authenticationDto.setEmail(AUTHENTICATION_DTO_EMAIL);
		when(request.getRemoteAddr()).thenReturn(HTTPSERVLET_REQUEST_IP);
		response = new Response();
		projectAuthenticationDto = new ProjectAuthenticationDto();
	}

	@Test
	public void method_Authenticate_should_return_response_userSecurityAuthorizationDto() throws EncryptedException {
		when(EncryptorResources.encryptIrreversible(anyString())).thenReturn(ENCRYPT_IRREVERSIBLE);
		when(securityFacade.userAuthentication(authenticationDto, request.getRemoteAddr()))
				.thenReturn(userSecurityAuthorizationDto);
		String autenticateResponseExpected = response
				.buildSuccess(securityFacade.userAuthentication(authenticationDto, request.getRemoteAddr())).toJson();
		assertEquals(autenticateResponseExpected, authenticationResource.authenticate(authenticationDto, request));
	}

	@Test(expected = HttpResponseException.class)
	public void method_Authenticate_should_throw_EncryptedException() throws EncryptedException {
		when(EncryptorResources.encryptIrreversible(anyString())).thenThrow(new EncryptedException());
		authenticationResource.authenticate(authenticationDto, request);
	}

	@Test
	public void method_projectAuthenticate_should_return_response_JWT()
			throws TokenException, AuthenticationException, JOSEException {
		String responseJWTExpected = response.buildSuccess(JWT).toJson();
		when(securityFacade.projectAuthentication(projectAuthenticationDto, request.getRemoteAddr().toString()))
				.thenReturn(JWT);
		assertEquals(responseJWTExpected,
				authenticationResource.projectAuthenticate(projectAuthenticationDto, request));
	}

	@Test
	public void method_invalidate_should_call_securityFacade_invalidate_method() {
		authenticationResource.invalidate(request);
		verify(securityFacade).invalidate(anyString());

	}
}
