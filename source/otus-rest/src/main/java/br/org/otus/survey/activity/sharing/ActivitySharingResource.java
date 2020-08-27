package br.org.otus.survey.activity.sharing;

import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.user.Secured;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharingDto;

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
  public String getSharedURL(@Context HttpServletRequest request, @PathParam("id") String activityId) {
    String token = AuthorizationHeaderReader.readToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    ActivitySharingDto activitySharingDto = activitySharingFacade.getSharedURL(activityId, token);
    return new Response().buildSuccess(activitySharingDto).toJson(ActivitySharingDto.getFrontGsonBuilder());
  }

  @PUT
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String renovateSharedURL(@Context HttpServletRequest request, @PathParam("id") String id) {
    String token = AuthorizationHeaderReader.readToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    ActivitySharingDto activitySharingDto = activitySharingFacade.renovateSharedURL(id, token);
    return new Response().buildSuccess(activitySharingDto).toJson(ActivitySharingDto.getFrontGsonBuilder());
  }

  @DELETE
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String deleteSharedURL(@Context HttpServletRequest request, @PathParam("id") String id) {
    String token = AuthorizationHeaderReader.readToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    activitySharingFacade.deleteSharedURL(id, token);
    return new Response().buildSuccess().toJson();
  }
}
