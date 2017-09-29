package org.ccem.otus.service.extraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ccem.otus.model.survey.activity.SurveyActivity;

import br.org.otus.api.Extractable;

public class SurveyActivityExtractor implements Extractable {

	private List<SurveyActivity> surveyActivities;
	private List<List<Object>> values;

	public SurveyActivityExtractor(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
	}

	@Override
	public Set<String> getHeaders() {
		return surveyActivities.get(0).getSurveyForm().getSurveyTemplate().getOrderedCustomIDs();
	}

	@Override
	public List<List<Object>> getValues() {
		this.values = new ArrayList<List<Object>>();
		for (SurveyActivity surveyActivity : surveyActivities) {
			// TODO: obter valores de repostas
		}
		return values;
	}

}
