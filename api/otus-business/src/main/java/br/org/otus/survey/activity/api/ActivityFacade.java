package br.org.otus.survey.activity.api;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import com.google.gson.JsonSyntaxException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.ActivityService;

import javax.inject.Inject;
import java.util.List;

public class ActivityFacade {

	@Inject
	private ActivityService activityService;

	public List<SurveyActivity> list(long rn, String userEmail) {
		return activityService.list(rn, userEmail);
	}

	public SurveyActivity getByID(String id) {
		try {
			return activityService.getByID(id);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public List<SurveyActivity> get(String acronym, Integer version){
		try {
			return activityService.get(acronym, version);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		} catch (MemoryExcededException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public String create(SurveyActivity surveyActivity) {
		return activityService.create(surveyActivity);
	}

	public SurveyActivity updateActivity(SurveyActivity surveyActivity) {
		try {
			return activityService.update(surveyActivity);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public boolean updateCheckerActivity(String checkerUpdated) {
		try {
			return activityService.updateCheckerActivity(checkerUpdated);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(
					ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
		}
	}

	@SuppressWarnings("static-access")
	public SurveyActivity deserialize(String surveyActivity) {
		try {
			return SurveyActivity.deserialize(surveyActivity);
		} catch (JsonSyntaxException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

}
