package br.org.otus.security;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;

import br.org.otus.security.user.AuthenticationFilter;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.security.services.SecurityContextService;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest {
  @InjectMocks
  private AuthenticationFilter authenticationFilter;
  @Mock
  private SecurityContextService securityContextService;
  @Mock
  private ContainerRequestContext containerRequestContext;
  private String authorizationHeaderReader = "Bearer jhdskajhkasdjhkdshkdsa";
  private ContainerRequestContext containerRequestContextException;

  @Before
  public void setUp() throws Exception {
    when(containerRequestContext.getHeaderString(anyString())).thenReturn(authorizationHeaderReader);
  }

  @Test(expected = Exception.class)
  public void method_filter_shoul_throw_exception() throws Exception {
    authenticationFilter.filter(containerRequestContextException);
  }

}
