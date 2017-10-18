package org.ccem.otus.service.extraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionHeadersFactory;
import org.ccem.otus.service.extraction.factories.SurveyActivityExtractionRecordsFactory;

import br.org.otus.api.Extractable;

public class SurveyActivityExtraction implements Extractable {

	private List<SurveyActivity> surveyActivities;
	private SurveyActivityExtractionHeadersFactory headersFactory;
	private SurveyActivityExtractionRecordsFactory recordsFactory;

	public SurveyActivityExtraction(List<SurveyActivity> surveyActivities) {
		this.headersFactory = new SurveyActivityExtractionHeadersFactory(this.surveyActivities);
		this.recordsFactory = new SurveyActivityExtractionRecordsFactory();
	}

	@Override
	public LinkedHashSet<String> getHeaders() {
		return this.headersFactory.getHeaders();
	}

	@Override
	public List<List<Object>> getValues() throws DataNotFoundException {
		List<List<Object>> values = new ArrayList<>();
		for (SurveyActivity surveyActivity : surveyActivities) {
			List<Object> resultInformation = new ArrayList<>();

			for (String s : this.headersFactory.getHeaders()) {
				this.recordsFactory.getSurveyInformation().put(s, "");
			}
			this.recordsFactory.getSurveyBasicInfo(surveyActivity);
			this.recordsFactory.getSurveyQuestionInfo(surveyActivity);
			resultInformation.addAll(new ArrayList<>(this.recordsFactory.getSurveyInformation().values()));
			values.add(resultInformation);
		}
		return values;
	}

}
