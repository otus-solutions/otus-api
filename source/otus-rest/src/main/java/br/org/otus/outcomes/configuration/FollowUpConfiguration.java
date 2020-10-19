package br.org.otus.outcomes.configuration;

import br.org.otus.outcomes.FollowUpFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/followUp")
public class FollowUpConfiguration {

  @Inject
  private FollowUpFacade followUpFacade;

  @PUT
  @Path("/add")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createFollowUp(String followUpJson) {
    return new Response().buildSuccess(followUpFacade.createFollowUp(followUpJson)).toJson();
  }

  @POST
  @Path("/update")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String updateFollowUp(String followUpJson) {
    return new Response().buildSuccess(followUpFacade.updateFollowUp(followUpJson)).toJson();
  }

  @DELETE
  @Path("/deactivate/{followUpId}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String deactivateFollowUp(@PathParam("followUpId") String followUpId) {
    return new Response().buildSuccess(followUpFacade.deactivateFollowUp(followUpId)).toJson();
  }

  @GET
  @Path("/list")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String listFollowUps() {
    return new Response().buildSuccess(followUpFacade.listFollowUps()).toJson();
  }

  @GET
  @Path("/list/{rn}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String listFollowUps(@PathParam("rn") String rn) {
    return new Response().buildSuccess(followUpFacade.listFollowUpsByParticipant(rn)).toJson();
  }

  @GET
  @Path("/participantEvent/{rn}/search/{id}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String searchParticipantEvent(@PathParam("rn") String rn, @PathParam("id") String id) {
    return new Response().buildSuccess(followUpFacade.searchParticipantEvent(rn, id)).toJson();
  }

  @PUT
  @Path("/participantEvent/cancel/{id}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String cancelParticipantEvent(@PathParam("id") String id) {
    return new Response().buildSuccess(followUpFacade.cancelParticipantEvent(id)).toJson();
  }

  @POST
  @Path("/participantEvent/add/{rn}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String startParticipantEvent(@PathParam("rn") String rn, String eventJson) {
    return new Response().buildSuccess(followUpFacade.startParticipantEvent(rn, eventJson)).toJson();
  }

  @PUT
  @Path("/participantEvent/accomplished/{id}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String accomplishedParticipantEvent(@PathParam("id") String id) {
    return new Response().buildSuccess(followUpFacade.accomplishedParticipantEvent(id)).toJson();
  }

  @GET
  @Path("/participantEvent/listAll/{rn}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String listAllParticipantEvents(@PathParam("rn") String rn) {
    return new Response().buildSuccess(followUpFacade.listAllParticipantEvents(rn)).toJson();
  }

}
