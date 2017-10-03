package org.ccem.otus.service.extraction;

import java.util.*;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;

import br.org.otus.api.Extractable;

public class SurveyActivityExtraction implements Extractable {

	private List<SurveyActivity> surveyActivities;
	private HashMap<String, Object> headers;
	private List<List<Object>> values;

	public SurveyActivityExtraction(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
		setHeaders();
	}

	private void setHeaders() {
		this.headers = new LinkedHashMap<>();

		// basic info headers
		headers.put("recruitment_number", surveyActivities.get(0).getParticipantData().getRecruitmentNumber());
		headers.put("acronym", "");
		headers.put("category", "");
		headers.put("type", "");
		headers.put("interviewer", "");
		headers.put("current_status", "");
		headers.put("current_status_date", "");
		headers.put("creation_date", "");
		headers.put("paper", "");
		headers.put("realization_date", "");
		headers.put("last_finalization_date", "");

		// answer headers
		surveyActivities.get(0).getSurveyForm().getSurveyTemplate().itemContainer.forEach(surveyItem -> {
			for (String header : surveyItem.getExtractionIDs()) {
				// if () isQuestion
				headers.put(header, "");
			}
			headers.put(surveyItem.getCustomID() + "_comment", "");
			headers.put(surveyItem.getCustomID() + "_metadata", "");
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
			for(QuestionFill fill : surveyAcfativity.getFillContainer().getFillingList())
			
			LinkedHashMap<String, Object> surveyMap = new LinkedHashMap<>(this.headers);
			// TODO: obter valores de repostas e adicionar a surveyMap
			// surveyMap.replace("q1", "answer");
			// this.values.addAll(surveyMap.values());
		}
		return values;
	}

}
