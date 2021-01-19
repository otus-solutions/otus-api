package br.org.otus;

import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

public abstract class AuthenticationResourceTestsParent extends ResourceTestsParent {

  protected static final String USER_EMAIL = "user@otus.com";
  protected static final String TOKEN = "347bcf7e-dcb2-4768-82eb-ee95d893f4c0";
  protected static final String AUTHORIZATION_HEADER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlci";

  @Mock
  protected HttpServletRequest request;
  @Mock
  protected SecurityContext securityContext;
  @Mock
  protected SessionIdentifier sessionIdentifier;
  @Mock
  protected AuthenticationData authenticationData;

  protected void mockContextToSetUserEmail() {
    /*
      Must add in child class: @PrepareForTest({AuthorizationHeaderReader.class})
     */
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(TOKEN);
    mockStatic(AuthorizationHeaderReader.class);
    when(AuthorizationHeaderReader.readToken(Mockito.any())).thenReturn(AUTHORIZATION_HEADER_TOKEN);
    when(securityContext.getSession(AUTHORIZATION_HEADER_TOKEN)).thenReturn(sessionIdentifier);
    when(sessionIdentifier.getAuthenticationData()).thenReturn(authenticationData);
    when(authenticationData.getUserEmail()).thenReturn(USER_EMAIL);
  }

}
