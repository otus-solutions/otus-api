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
}
