package br.org.otus.user.pendency;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.user.api.pendency.UserActivityPendencyFacade;

@Path("pendency/user-activity-pendency")
public class UserActivityPendencyResource {

  @Inject
  private UserActivityPendencyFacade userActivityPendencyFacade;

  @Inject
  private SecurityContext securityContext;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String create(@Context HttpServletRequest request, String userActivityPendencyJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    userActivityPendencyFacade.create(userActivityPendencyJson, userEmail);
    return (new Response()).buildSuccess().toJson();
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String update(@PathParam("id") String id, String newDataForUserActivityPendencyJson) {
    userActivityPendencyFacade.update(newDataForUserActivityPendencyJson);
    return (new Response()).buildSuccess().toJson();
  }

  @GET
  @Secured
  @Path("/{id}/{state}")
  @Produces(MediaType.APPLICATION_JSON)
  public String get(@PathParam("id") String id, @PathParam("state") String state) {
    System.out.println("here");
    List<UserActivityPendency> userActivityPendencyList = userActivityPendencyFacade.list();
    return (new Response()).buildSuccess(userActivityPendencyList).toJson();
  }

  @GET
  @Secured
  @Path("/{activityId}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getByActivityId(@PathParam("activityId") String id) {
    UserActivityPendency userActivityPendency = userActivityPendencyFacade.getByActivityId(id);
    return (new Response()).buildSuccess(userActivityPendency).toJson();
  }

  @DELETE
  @Secured
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String delete(@PathParam("id") String id) {
    userActivityPendencyFacade.delete(id);
    return (new Response()).buildSuccess().toJson();
  }

}
