package br.org.otus.participant;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.user.dto.PasswordResetDto;
import org.ccem.otus.participant.model.Participant;

import br.org.otus.model.User;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.user.api.UserFacade;

@Path("/participants")
public class ParticipantResource {

  @Inject
  private ParticipantFacade participantFacade;
  @Inject
  private UserFacade userFacade;
  @Inject
  private SecurityContext securityContext;

  @GET
  @Secured
  public String getAll(@Context HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    User user = userFacade.fetchByEmail(userEmail);
    return new Response().buildSuccess(participantFacade.list(user.getFieldCenter())).toJson(Participant.getGsonBuilder());
  }

  @GET
  @Path("/{rn}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  public Participant getByRecruitmentNumber(@PathParam("rn") long rn) {
    return participantFacade.getByRecruitmentNumber(rn);
  }

  @POST
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String create(@Context HttpServletRequest request, String participantJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    return new Response().buildSuccess(participantFacade.create(participantJson, userEmail)).toJson(Participant.getGsonBuilder());
  }

  @POST
  @Path("/registerPassword")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String registerPassword(PasswordResetDto resetData, @Context HttpServletRequest request) {
    participantFacade.registerPassword(resetData);
    return new Response().buildSuccess().toJson();
  }

  @POST
  @Path("/password-reset")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String requestRecovery(PasswordResetRequestDto requestData) {
    Response response = new Response();
    participantFacade.requestPasswordReset(requestData);
    return response.buildSuccess().toJson();
  }

  @POST
  @Path("/password-reset-link")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String requestPasswordResetLink(PasswordResetRequestDto requestData) {
    Response response = new Response();
    String link = participantFacade.requestPasswordResetLink(requestData);
    return response.buildSuccess(link).toJson();
  }

  @PUT
  @Path("/update")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String update(@Context HttpServletRequest request, String participantJson){
    return new Response().buildSuccess(participantFacade.update(participantJson)).toJson(Participant.getGsonBuilder());
  }

  @PUT
  @Path("/edit/{participantId}/{email}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String editEmail(@Context HttpServletRequest request, @PathParam("participantId") String participantId, @PathParam("email") String email){
    return new Response().buildSuccess(participantFacade.editEmail(participantId, email)).toJson();
  }
}
