package br.org.otus;

import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

public abstract class AuthenticationResource {

  @Inject
  protected SecurityContext securityContext;

  protected String getToken(HttpServletRequest request) {
    return request.getHeader(HttpHeaders.AUTHORIZATION);
  }

  protected String getUserEmailToken(HttpServletRequest request) {
    String token = getToken(request);
    return securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
  }
}
