package br.org.otus.sharing;

import br.org.otus.rest.Response;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.user.Secured;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.activity.sharing.ActivitySharingFacade;
import org.ccem.otus.model.survey.activity.SurveyActivity;

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

  @Inject
  private ActivityFacade activityFacade;

  @Inject
  private SecurityContext securityContext;

  @GET
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String getSharedLink(@Context HttpServletRequest request, @PathParam("id") String id) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String sharedLink = activitySharingFacade.getSharedLink(id, token);
    return new Response().buildSuccess(sharedLink).toJson();
  }

  @PUT
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String recreateSharedLink(@Context HttpServletRequest request, @PathParam("id") String id) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String sharedLink = activitySharingFacade.recreateSharedLink(id, token);
    return new Response().buildSuccess(sharedLink).toJson();
  }

  @DELETE
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String deleteSharedLink(@Context HttpServletRequest request, @PathParam("id") String id) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    activitySharingFacade.deleteSharedLink(id, token);
    return new Response().buildSuccess().toJson();
  }
}
