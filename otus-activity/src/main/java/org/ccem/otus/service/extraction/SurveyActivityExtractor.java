package org.ccem.otus.service.extraction;

import org.ccem.otus.model.survey.activity.SurveyActivity;

import java.util.List;

public class SurveyActivityExtractor implements Extractable {

	private List<SurveyActivity> surveyActivities;


	public void setSurveyActivities(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
	}

	@Override
	public List<String> getHeaders() {
		return null;
	}

	@Override
	public List<Object> getValues() {
		return null;
	}
}
