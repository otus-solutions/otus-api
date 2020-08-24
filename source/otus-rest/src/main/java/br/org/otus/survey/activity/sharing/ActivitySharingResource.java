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
  public String getSharedLink(@Context HttpServletRequest request, @PathParam("id") String id) {
    String token = AuthorizationHeaderReader.readToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    String sharedLink = activitySharingFacade.getSharedLink(id, token);
    return new Response().buildSuccess(sharedLink).toJson();
  }

  @PUT
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String recreateSharedLink(@Context HttpServletRequest request, @PathParam("id") String id) {
    String token = AuthorizationHeaderReader.readToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    String sharedLink = activitySharingFacade.recreateSharedLink(id, token);
    return new Response().buildSuccess(sharedLink).toJson();
  }

  @DELETE
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String deleteSharedLink(@PathParam("id") String id) {
    activitySharingFacade.deleteSharedLink(id);
    return new Response().buildSuccess().toJson();
  }
}
