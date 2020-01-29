package br.org.otus.security.user;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import br.org.otus.response.info.Authorization;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.services.SecurityContextService;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  @Inject
  private SecurityContextService securityContextService;

  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {
    String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

    try {
      securityContextService.validateToken(AuthorizationHeaderReader.readToken(authorizationHeader));

    } catch (Exception e) {
      containerRequestContext.abortWith(Authorization.build().toResponse());
    }
  }
}
