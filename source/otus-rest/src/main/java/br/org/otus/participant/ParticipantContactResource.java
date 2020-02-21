package br.org.otus.participant;

import br.org.otus.participant.api.ParticipantContactFacade;
import br.org.otus.rest.Response;
import org.ccem.otus.participant.model.ParticipantContact;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("participant/participant-contact")
public class ParticipantContactResource {

  @Inject
  private ParticipantContactFacade participantContactFacade;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(String participantContactJson) {
    String participantContactID = participantContactFacade.create(participantContactJson);
    return (new Response()).buildSuccess(participantContactID).toJson();
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String update(@PathParam("id") String participantContactID, String participantContactJson) {
    participantContactFacade.update(participantContactID, participantContactJson);
    return (new Response()).buildSuccess().toJson();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String delete(@PathParam("id") String participantContactID) {
    participantContactFacade.delete(participantContactID);
    return (new Response()).buildSuccess().toJson();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@PathParam("id") String participantContactID) {
    ParticipantContact participantContact = participantContactFacade.get(participantContactID);
    return (new Response()).buildSuccess(participantContact)
      .toJson(ParticipantContact.getFrontGsonBuilder());
  }

}
