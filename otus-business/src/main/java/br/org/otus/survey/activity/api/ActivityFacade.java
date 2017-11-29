package br.org.otus.survey.activity.api;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.ActivityService;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

import com.google.gson.JsonSyntaxException;

public class ActivityFacade {

	@Inject
	private ActivityService activityService;

	public List<SurveyActivity> list(long rn) {
		return activityService.list(rn);		
	}

	public SurveyActivity getByID(String id) {
		try {
			return activityService.getByID(id);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}
	
	public List<SurveyActivity> getAllByID(String id) {
		try {
			return activityService.getAllByID(id);
		} catch (DataNotFoundException e) {
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

	@SuppressWarnings("static-access")
	public SurveyActivity deserialize(String surveyActivity) {
		try {
			return SurveyActivity.deserialize(surveyActivity);
		} catch (JsonSyntaxException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

}
