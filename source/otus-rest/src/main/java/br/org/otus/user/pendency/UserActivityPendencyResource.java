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
    String pendencyId = userActivityPendencyFacade.create(getUserEmail(request), userActivityPendencyJson);
    return (new Response()).buildSuccess(pendencyId).toJson();
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String update(@PathParam("id") String userActivityPendencyID, String newDataForUserActivityPendencyJson) {
    userActivityPendencyFacade.update(userActivityPendencyID, newDataForUserActivityPendencyJson);
    return (new Response()).buildSuccess().toJson();
  }

  @DELETE
  @Secured
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String delete(@PathParam("id") String id) {
    userActivityPendencyFacade.delete(id);
    return (new Response()).buildSuccess().toJson();
  }

  @GET
  @Secured
  @Path("/{activityId}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getByActivityId(@PathParam("activityId") String id) {
    UserActivityPendency userActivityPendency = userActivityPendencyFacade.getByActivityId(id);
    return (new Response()).buildSuccess(userActivityPendency)
      .toJson(UserActivityPendency.getFrontGsonBuilder());
  }

  @GET
  @Secured
  @Path("/list")
  @Produces(MediaType.APPLICATION_JSON)
  public String listAllPendencies(@Context HttpServletRequest request) {
    List<UserActivityPendency> userActivityPendencyList = userActivityPendencyFacade.listAllPendencies(getUserEmail(request));
    return (new Response()).buildSuccess(userActivityPendencyList)
      .toJson(UserActivityPendency.getFrontGsonBuilder());
  }

  @GET
  @Secured
  @Path("/list/opened")
  @Produces(MediaType.APPLICATION_JSON)
  public String listOpenedPendencies(@Context HttpServletRequest request) {
    List<UserActivityPendency> userActivityPendencyList = userActivityPendencyFacade.listOpenedPendencies(getUserEmail(request));
    return (new Response()).buildSuccess(userActivityPendencyList)
      .toJson(UserActivityPendency.getFrontGsonBuilder());
  }

  @GET
  @Secured
  @Path("/list/done")
  @Produces(MediaType.APPLICATION_JSON)
  public String listDonePendencies(@Context HttpServletRequest request) {
    List<UserActivityPendency> userActivityPendencyList = userActivityPendencyFacade.listDonePendencies(getUserEmail(request));
    return (new Response()).buildSuccess(userActivityPendencyList)
      .toJson(UserActivityPendency.getFrontGsonBuilder());
  }

  private String getUserEmail(HttpServletRequest request){
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    return securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
  }

}
