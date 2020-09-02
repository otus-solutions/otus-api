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
import org.ccem.otus.enums.AuthenticationMode;
import org.ccem.otus.exceptions.webservice.security.TokenException;

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

      switch(AuthenticationMode.valueFromName(mode)){
        case USER:
        case CLIENT:
          securityContextService.validateToken(token);
          break;

        case PARTICIPANT:
          securityFacade.validateToken(AuthorizationHeaderReader.readToken(authorizationHeader));
          break;

        case ACTIVITY_SHARING:
          securityFacade.validateActivitySharingToken(AuthorizationHeaderReader.readToken(authorizationHeader));
          break;

        default:
          throw new TokenException("Invalid token mode");
      }

    } catch (Exception e) {
      containerRequestContext.abortWith(Authorization.build().toResponse());
    }
  }
}
