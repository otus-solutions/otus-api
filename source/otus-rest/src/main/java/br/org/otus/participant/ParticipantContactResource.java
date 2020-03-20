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
    String id = participantContactFacade.create(participantContactJson);
    return (new Response()).buildSuccess(id).toJson();
  }

  @PUT
  @Path("/update/email")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String updateEmail(String participantContactDtoJson) {
    participantContactFacade.updateEmail(participantContactDtoJson);
    return (new Response()).buildSuccess().toJson();
  }

  @PUT
  @Path("/update/address")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String updateAddress(String participantContactDtoJson) {
    participantContactFacade.updateAddress(participantContactDtoJson);
    return (new Response()).buildSuccess().toJson();
  }

  @PUT
  @Path("/update/phone-number")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String updatePhoneNumber(String participantContactDtoJson) {
    participantContactFacade.updatePhoneNumber(participantContactDtoJson);
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
  @Path("/rn/{rn}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getByRecruitmentNumber(@PathParam("rn") String recruitmentNumber) {
    ParticipantContact participantContact = participantContactFacade.getByRecruitmentNumber(recruitmentNumber);
    return (new Response()).buildSuccess(participantContact)
      .toJson(ParticipantContact.getFrontGsonBuilder());
  }

}
