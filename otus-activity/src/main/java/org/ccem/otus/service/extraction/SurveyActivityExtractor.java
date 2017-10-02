package org.ccem.otus.service.extraction;

import java.util.*;

import org.ccem.otus.model.survey.activity.SurveyActivity;

import br.org.otus.api.Extractable;

public class SurveyActivityExtractor implements Extractable {

	private List<SurveyActivity> surveyActivities;
	private HashMap<String, Object> headers;
	private List<List<Object>> values;

	public SurveyActivityExtractor(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
		setHeaders();
	}

	private void setHeaders() {
		this.headers= new LinkedHashMap<>();

		surveyActivities.get(0).getSurveyForm().getSurveyTemplate().itemContainer.forEach(surveyItem -> {
			for (String header : surveyItem.getExtractionIDs()) headers.put(header, "");
		});
	}

	@Override
	public Set<String> getHeaders() {
		return headers.keySet();
	}

	@Override
	public List<List<Object>> getValues() {
		this.values = new ArrayList<List<Object>>();
		for (SurveyActivity surveyAcfativity : surveyActivities) {
			// TODO: obter valores de repostas
		}
		return values;
	}

}
