package br.org.otus.security.rest;



import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.verify;




import javax.servlet.http.HttpServletRequest;

import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.rest.Response;
import br.org.otus.security.EncryptorResources;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.AuthenticationDto;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;


@RunWith(PowerMockRunner.class)
@PrepareForTest(EncryptorResources.class)
public class AuthenticationResourceTest {
	
	@InjectMocks
	AuthenticationResource authenticationResource;		
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	SecurityFacade securityFacade;	
	
	@InjectMocks
	UserSecurityAuthorizationDto userSecurityAuthorizationDto;
	
	
	private AuthenticationDto authenticationDto;

	private Response response;
	

	@Before	
	public void setUp() throws Exception {
		mockStatic(EncryptorResources.class);
		authenticationDto = new AuthenticationDto();
		authenticationDto.setEmail("otus@otus.com");
		response = new Response();		
		
	}

	@Test
	public void method_Authenticate_should_return_response_userSecurityAuthorizationDto() throws EncryptedException {
		when(EncryptorResources.encryptIrreversible(anyString())).thenReturn("123");
		when(request.getRemoteAddr()).thenReturn("143.54.220.57");
		when(securityFacade.userAuthentication(authenticationDto, request.getRemoteAddr())).thenReturn(userSecurityAuthorizationDto);
		
		String autenticateResponseExpected = response.buildSuccess(securityFacade.userAuthentication(authenticationDto, request.getRemoteAddr())).toJson();		
		System.out.println(autenticateResponseExpected);
				
		assertEquals(autenticateResponseExpected,authenticationResource.authenticate(authenticationDto, request));
		
		}
	
	@Test(expected = HttpResponseException.class)	
	public void method_Authenticate_should_throw_EncryptedException() throws EncryptedException{	
		when(EncryptorResources.encryptIrreversible(anyString())).thenThrow(new EncryptedException());
		authenticationResource.authenticate(authenticationDto, request);
		
		
		
	}

	@Test
	public void method_ProjectAuthenticate(){
		
	}
		
	@Test
	public void method_Invalidate() {
		
	}

}
