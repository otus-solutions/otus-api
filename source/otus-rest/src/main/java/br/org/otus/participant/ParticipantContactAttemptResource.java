package br.org.otus.participant;

import br.org.otus.participant.api.ParticipantContactAttemptFacade;
import br.org.otus.rest.Response;
import org.ccem.otus.participant.model.participantContactAttempt.MetadataAttemptStatus;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("participant/participant-contact-attempt")
public class ParticipantContactAttemptResource {

  @Inject
  private ParticipantContactAttemptFacade participantContactAttemptFacade;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(String participantContactJson) {
    String id = participantContactAttemptFacade.create(participantContactJson);
    return (new Response()).buildSuccess(id).toJson();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String delete(@PathParam("id") String participantContactAttemptID) {
    participantContactAttemptFacade.delete(participantContactAttemptID);
    return (new Response()).buildSuccess().toJson();
  }

  @GET
  @Path("/{rn}/{contactType}/{position}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAttempts(@PathParam("rn") Long recruitmentNumber, @PathParam("contactType") String objectType, @PathParam("position") String position) {
    ArrayList<ParticipantContactAttempt> participantContact = participantContactAttemptFacade.findAttempts(recruitmentNumber, objectType, position);
    return (new Response()).buildSuccess(participantContact)
      .toJson(ParticipantContactAttempt.getGsonBuilder());
  }

  @GET
  @Path("/metadata-status/{objectType}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findMetadataAttempt(@PathParam("objectType") String objectType) {
    MetadataAttemptStatus metadataAttemptStatus = participantContactAttemptFacade.findMetadataAttempt(objectType);
    return (new Response()).buildSuccess(metadataAttemptStatus)
      .toJson(ParticipantContactAttempt.getGsonBuilder());
  }

}
