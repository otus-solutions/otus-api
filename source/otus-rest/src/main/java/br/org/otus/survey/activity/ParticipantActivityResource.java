package br.org.otus.survey.activity;

import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.security.context.SecurityContext;
import br.org.otus.security.user.Secured;
import br.org.otus.survey.activity.activityRevision.ActivityRevisionFacade;
import br.org.otus.survey.activity.api.ActivityFacade;
import org.ccem.otus.importation.activity.ActivityImportDTO;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("participants/{rn}/activities")
public class ParticipantActivityResource {

  @Inject
  private ActivityRevisionFacade activityRevisionFacade;
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

  @PUT
  @Secured
  @Path("offline")
  @Consumes(MediaType.APPLICATION_JSON)
  public String synchronizeOfflineActivities(@PathParam("rn") long rn, String surveyActivities) {
    isValidRecruitmentNumber(rn);
    ActivityImportDTO activityImportDTO = ActivityImportDTO.deserialize(surveyActivities);
    activityFacade.synchronize(rn, activityImportDTO);
    return new Response().buildSuccess().toJson();
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

  @PUT
  @Secured
  @Path("/update-checker-activity")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public String updateCheckerActivity(@PathParam("rn") long rn, String checkerUpdated) {
    isValidRecruitmentNumber(rn);
    boolean updateCheckerActivity = activityFacade.updateCheckerActivity(checkerUpdated);
    return new Response().buildSuccess(updateCheckerActivity).toJson();
  }

  @GET
  @Secured
  @Path("/revision/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getActivityRevisions(@Context HttpServletRequest request, @PathParam("id") String activityID) {
    return new Response().buildSuccess(activityRevisionFacade.getActivityRevisions(activityID)).toSurveyJson();
  }

  @POST
  @Secured
  @Path("/revision")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public String createActivityRevision(@Context HttpServletRequest request, String activityRevisionJson) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    String userEmail = securityContext.getSession(AuthorizationHeaderReader.readToken(token)).getAuthenticationData().getUserEmail();
    activityRevisionFacade.create(activityRevisionJson, userEmail);
    return new Response().buildSuccess().toJson();
  }

  private void isValidRecruitmentNumber(long rn) {
    participantFacade.getByRecruitmentNumber(rn);
  }
}
