package org.ccem.otus.service.extraction;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.survey.template.SurveyTemplate;

import java.util.List;
import java.util.Set;

public class SurveyActivityExtractor implements Extractable {


	public SurveyActivityExtractor(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
	}

	private List<SurveyActivity> surveyActivities;


	public void setSurveyActivities(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
	}

	@Override
	public Set<String> getHeaders() {
		return headerExtractor(surveyActivities.get(0).getSurveyForm().getSurveyTemplate());
	}

	@Override
	public List<Object> getValues() {
		return null;
	}

	private Set<String> headerExtractor(SurveyTemplate surveyTemplate){
		final Set<String> orderedCustomIDs = surveyTemplate.getOrderedCustomIDs();
		return orderedCustomIDs;
	}
}
