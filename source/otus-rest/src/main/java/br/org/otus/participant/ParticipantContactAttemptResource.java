package br.org.otus.participant;

import br.org.otus.participant.api.ParticipantContactAttemptFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.user.Secured;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAddressAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttemptConfiguration;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("participant/participant-contact-attempt")
public class ParticipantContactAttemptResource {

  @Inject
  private ParticipantContactAttemptFacade participantContactAttemptFacade;

  @Inject
  private SecurityContext securityContext;

  @POST
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(@Context HttpServletRequest request, String participantContactJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    String id = participantContactAttemptFacade.create(participantContactJson, userEmail);
    return (new Response()).buildSuccess(id).toJson();
  }

  @DELETE
  @Secured
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String delete(@PathParam("id") String participantContactAttemptID) {
    participantContactAttemptFacade.delete(participantContactAttemptID);
    return (new Response()).buildSuccess().toJson();
  }

  @GET
  @Secured
  @Path("/{rn}/{contactType}/{position}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAttempts(@PathParam("rn") Long recruitmentNumber, @PathParam("contactType") String objectType, @PathParam("position") String position) {
    ArrayList<ParticipantContactAddressAttempt> participantContact = participantContactAttemptFacade.findAddressAttempts(recruitmentNumber, objectType, position);
    return (new Response()).buildSuccess(participantContact)
      .toJson(ParticipantContactAttempt.getGsonBuilder());
  }

  @GET
  @Secured
  @Path("/attempt-configuration/{objectType}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findMetadataAttempt(@PathParam("objectType") String objectType) {
    ParticipantContactAttemptConfiguration metadataAttemptStatus = participantContactAttemptFacade.findMetadataAttempt(objectType);
    return (new Response()).buildSuccess(metadataAttemptStatus)
      .toJson(ParticipantContactAttempt.getGsonBuilder());
  }
}
