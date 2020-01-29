package br.org.otus.security.participant;

import br.org.otus.response.info.Authorization;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.api.SecurityFacade;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@ParticipantSecured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ParticipantAuthenticationFilter implements ContainerRequestFilter {

  @Inject
  private SecurityFacade securityFacade;

  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {
    String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    try {
      securityFacade.validateToken(AuthorizationHeaderReader.readToken(authorizationHeader));
    } catch (Exception e) {
      containerRequestContext.abortWith(Authorization.build().toResponse());
    }
  }
}
