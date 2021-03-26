package br.org.otus.survey.activity;

import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;
import br.org.otus.survey.activity.api.ActivityFacade;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("activities")
public class ActivityResource {

  @Inject
  private ActivityFacade activityFacade;

  @GET
  @Secured
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getByID(@PathParam("id") String id) {
    return new Response().buildSuccess(activityFacade.getByID(id)).toSurveyJson();
  }

  @PUT
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String update(@Context HttpServletRequest request, String surveyActivity) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    SurveyActivity deserializedSurveyActivity = activityFacade.deserialize(surveyActivity);
    SurveyActivity updatedActivity = activityFacade.updateActivity(deserializedSurveyActivity, token);
    return new Response().buildSuccess(updatedActivity).toSurveyJson();
  }


  @GET
  @Path("/teste-async/{method}/{seconds}")
  @Produces(MediaType.APPLICATION_JSON)
  public String testeAsync(@PathParam("method") String method, @PathParam("seconds") String seconds)
  {
    String response = activityFacade.testeAsync(method, seconds);
    return new Response().buildSuccess(response).toJson();
  }
}
