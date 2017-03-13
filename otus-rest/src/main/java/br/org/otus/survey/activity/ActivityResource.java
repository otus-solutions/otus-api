package br.org.otus.survey.activity;

import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.rest.Response;
import br.org.otus.security.Secured;
import br.org.otus.survey.activity.api.ActivityFacade;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("participants/{rn}/activities")
public class ActivityResource {

	@Inject
	private ActivityFacade activityFacade;
	@Inject
	private ParticipantFacade participantFacade;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public String getAll(@PathParam("rn") long rn) {
		isValidRecruitmentNumber(rn);

		return new Response().buildSuccess(activityFacade.list(rn)).toSurveyJson();
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

	private void isValidRecruitmentNumber(long rn) {
		participantFacade.getByRecruitmentNumber(rn);
	}

}
