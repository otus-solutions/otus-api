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
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.services.SecurityContextService;
import com.nimbusds.jwt.SignedJWT;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  @Inject
  private SecurityContextService securityContextService;

  @Inject
  private SecurityFacade securityFacade;

  @Override
  public void filter(ContainerRequestContext containerRequestContext) {
    String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    try {
      String token = AuthorizationHeaderReader.readToken(authorizationHeader);
      SignedJWT parsed = SignedJWT.parse(token);
      String mode = parsed.getJWTClaimsSet().getClaim("mode").toString();

      switch(mode){
        case "user":
        case "client":
          securityContextService.validateToken(token);
          break;

        case "participant":
          securityFacade.validateToken(AuthorizationHeaderReader.readToken(authorizationHeader));
          break;

        case "sharing":
          securityFacade.validateActivitySharingToken(AuthorizationHeaderReader.readToken(authorizationHeader));
          break;

        default:
          throw new Exception("Invalid mode");
      }

    } catch (Exception e) {
      containerRequestContext.abortWith(Authorization.build().toResponse());
    }
  }
}
