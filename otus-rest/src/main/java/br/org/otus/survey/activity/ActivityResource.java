package br.org.otus.survey.activity;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import br.org.otus.survey.activity.activityReview.ActivityReviewFacade;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.Secured;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.survey.activity.api.ActivityFacade;

@Path("participants/{rn}/activities")
public class ActivityResource {

  @Inject
  private ActivityReviewFacade activityReviewFacade;
  @Inject
  private ActivityFacade activityFacade;
  @Inject
  private ParticipantFacade participantFacade;

  @Inject
  private SecurityContext securityContext;

  @GET
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  public String getAll(@Context HttpServletRequest request, @PathParam("rn") long rn) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    isValidRecruitmentNumber(rn);

    return new Response().buildSuccess(activityFacade.list(rn, userEmail)).toSurveyJson();
  }

  @POST
  @Secured
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createActivity(@PathParam("rn") long rn, String surveyActivity) {
    isValidRecruitmentNumber(rn);
    String objectID = activityFacade.create(activityFacade.deserialize(surveyActivity));

    return new Response().buildSuccess(objectID).toJson();
  }

  @GET
  @Secured
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getByID(@PathParam("rn") long rn, @PathParam("id") String id) {
    isValidRecruitmentNumber(rn);

    return new Response().buildSuccess(activityFacade.getByID(id)).toSurveyJson();
  }

  @PUT
  @Secured
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String update(@PathParam("rn") long rn, @PathParam("id") String id, String surveyActivity) {
    isValidRecruitmentNumber(rn);
    SurveyActivity deserializedSurveyActivity = activityFacade.deserialize(surveyActivity);
    SurveyActivity updatedActivity = activityFacade.updateActivity(deserializedSurveyActivity);

    return new Response().buildSuccess(updatedActivity).toSurveyJson();
  }

//  @GET
//  @Secured
//  @Produces(MediaType.APPLICATION_JSON)
//  public String getAll(@Context HttpServletRequest request, @PathParam("rn") long rn) {
//    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
//    isValidRecruitmentNumber(rn);
//
//    return new Response().buildSuccess(activityReviewFacade.list(rn, userEmail)).toSurveyJson();
//  }

  @POST
  @Secured
  @Path("/review")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createActivityReview(@PathParam("rn") long rn, String activityReview) {
    isValidRecruitmentNumber(rn);
    String objectID = activityReviewFacade.create(activityReviewFacade.deserialize(activityReview));

    return new Response().buildSuccess(objectID).toJson();
  }

  private void isValidRecruitmentNumber(long rn) {
    participantFacade.getByRecruitmentNumber(rn);
  }

}
