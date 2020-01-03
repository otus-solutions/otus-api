package br.org.otus.security.participant;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.Validation;
import br.org.otus.rest.Response;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.AuthenticationDto;
import br.org.otus.security.dtos.ParticipantSecurityAuthorizationDto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/participant-authentication")
public class ParticipantAuthenticationResource {

  @Inject
  private SecurityFacade securityFacade;

  @POST
  @Path("/auth")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String authenticate(AuthenticationDto authenticationDto, @Context HttpServletRequest request) {
    try {
      authenticationDto.encrypt();
      Response response = new Response();
      ParticipantSecurityAuthorizationDto participantSecurityAuthorizationDto = securityFacade.participantAuthentication(authenticationDto);
      return response.buildSuccess(participantSecurityAuthorizationDto).toJson();
    } catch (EncryptedException e) {
      throw new HttpResponseException(Validation.build());
    }
  }

  @POST
  @Path("/invalidate")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String invalidate(AuthenticationDto authenticationDto, @Context HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    securityFacade.invalidateParticipantAuthentication(authenticationDto.userEmail, token);
    return new Response().buildSuccess().toJson();
  }
}
