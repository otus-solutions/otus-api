package br.org.otus.survey.activity.sharing;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("activity-sharing")
public class ActivitySharingResource {

  @Inject
  private ActivitySharingFacade activitySharingFacade;

  @GET
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getSharedURL(@Context HttpServletRequest request, @PathParam("id") String id) {
    String token = AuthorizationHeaderReader.readToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    String sharedURL = activitySharingFacade.getSharedURL(id, token);
    return new Response().buildSuccess(sharedURL).toJson();
  }

  @PUT
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String recreateSharedURL(@Context HttpServletRequest request, @PathParam("id") String id) {
    String token = AuthorizationHeaderReader.readToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    String sharedURL = activitySharingFacade.recreateSharedURL(id, token);
    return new Response().buildSuccess(sharedURL).toJson();
  }

  @DELETE
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String deleteSharedURL(@PathParam("id") String id) {
    activitySharingFacade.deleteSharedURL(id);
    return new Response().buildSuccess().toJson();
  }
}
