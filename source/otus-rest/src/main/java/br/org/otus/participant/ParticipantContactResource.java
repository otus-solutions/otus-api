package br.org.otus.participant;

import br.org.otus.participant.api.ParticipantContactFacade;
import br.org.otus.rest.Response;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;

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

  @PUT
  @Path("/update-main")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String updateMainContact(String participantContactDtoJson) {
    participantContactFacade.updateMainContact(participantContactDtoJson);
    return (new Response()).buildSuccess().toJson();
  }

  @PUT
  @Path("/add-secondary")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String addSecondaryContact(String participantContactDtoJson) {
    participantContactFacade.addSecondaryContact(participantContactDtoJson);
    return (new Response()).buildSuccess().toJson();
  }

  @PUT
  @Path("/update-secondary")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String updateSecondaryContact(String participantContactDtoJson) {
    participantContactFacade.updateSecondaryContact(participantContactDtoJson);
    return (new Response()).buildSuccess().toJson();
  }

  @PUT
  @Path("/swap")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String swapMainContactWithSecondary(String participantContactDtoJson) {
    participantContactFacade.swapMainContactWithSecondary(participantContactDtoJson);
    return (new Response()).buildSuccess().toJson();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String delete(@PathParam("id") String participantContactID) {
    participantContactFacade.delete(participantContactID);
    return (new Response()).buildSuccess().toJson();
  }

  @DELETE
  @Path("/secondary")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String deleteSecondaryContact(String participantContactDtoJson) {
    participantContactFacade.deleteSecondaryContact(participantContactDtoJson);
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

  @GET
  @Path("/nr/{nr}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getByRecruitmentNumber(@PathParam("nr") String recruitmentNumber) {
    ParticipantContact participantContact = participantContactFacade.getByRecruitmentNumber(recruitmentNumber);
    return (new Response()).buildSuccess(participantContact)
      .toJson(ParticipantContact.getFrontGsonBuilder());
  }

}
