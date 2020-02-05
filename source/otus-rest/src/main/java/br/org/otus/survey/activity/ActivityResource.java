package br.org.otus.survey.activity;

import br.org.otus.rest.Response;
import br.org.otus.security.user.Secured;
import br.org.otus.survey.activity.api.ActivityFacade;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import javax.inject.Inject;
import javax.ws.rs.*;
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
  public String update(String surveyActivity) {
    SurveyActivity deserializedSurveyActivity = activityFacade.deserialize(surveyActivity);
    SurveyActivity updatedActivity = activityFacade.updateActivity(deserializedSurveyActivity);
    return new Response().buildSuccess(updatedActivity).toSurveyJson();
  }


}
