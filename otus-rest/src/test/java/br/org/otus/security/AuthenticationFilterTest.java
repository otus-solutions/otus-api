package br.org.otus.security;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;

import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.security.services.SecurityContextService;

@RunWith(PowerMockRunner.class)
public class AuthenticationFilterTest {

	@InjectMocks
	AuthenticationFilter authenticationFilter;
	@Mock
	SecurityContextService securityContextService;
	@Mock
	ContainerRequestContext containerRequestContext;

	String authorizationHeaderReader = "Bearer jhdskajhkasdjhkdshkdsa";
	ContainerRequestContext containerRequestContextException;

	@Before
	public void setUp() throws Exception {
		when(containerRequestContext.getHeaderString(anyString())).thenReturn(authorizationHeaderReader);
	}

	@Test
	public void method_filter_should_call_validateToken() throws IOException, TokenException {
		authenticationFilter.filter(containerRequestContext);
		verify(containerRequestContext).getHeaderString(anyString());
		verify(securityContextService).validateToken(anyString());
	}

	@Test(expected = Exception.class)
	public void method_filter_shoul_throw_exception() throws Exception {
		authenticationFilter.filter(containerRequestContextException);
	}

}
