package br.org.otus.outcomes.configuration;

import br.org.otus.outcomes.FollowUpFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/event")
public class FollowUpEventConfiguration {

  @Inject
  private FollowUpFacade followUpFacade;

  @PUT
  @Path("/create/{followUpId}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createFollowUpEvent(@PathParam("followUpId") String followUpId, String EventJson) {
    return new Response().buildSuccess(followUpFacade.createFollowUpEvent(followUpId, EventJson)).toJson();
  }

  @DELETE
  @Path("/remove/{eventId}")
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  public String updateFollowUp(@PathParam("eventId") String eventId) {
    return new Response().buildSuccess(followUpFacade.removeFollowUpEvent(eventId)).toJson();
  }
}
