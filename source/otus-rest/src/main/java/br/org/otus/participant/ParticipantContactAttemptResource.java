package br.org.otus.participant;

import br.org.otus.participant.api.ParticipantContactAttemptFacade;
import br.org.otus.rest.Response;
import org.ccem.otus.participant.model.ParticipantContactAttempt;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;

import javax.inject.Inject;
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
  @Path("/rn/{rn}")
  @Produces(MediaType.APPLICATION_JSON)
  public String findAttempts(@PathParam("rn") Long recruitmentNumber) {
    ArrayList<ParticipantContactAttempt> participantContact = participantContactAttemptFacade.findAttempts(recruitmentNumber);
    return (new Response()).buildSuccess(participantContact)
      .toJson(ParticipantContactAttempt.getGsonBuilder());
  }

}
