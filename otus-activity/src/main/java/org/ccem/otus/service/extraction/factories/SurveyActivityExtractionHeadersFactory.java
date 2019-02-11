package org.ccem.otus.service.extraction.factories;

import java.util.LinkedList;
import java.util.List;

import org.ccem.otus.service.extraction.enums.SurveyActivityExtractionHeaders;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.item.questions.Question;

public class SurveyActivityExtractionHeadersFactory {

	private SurveyForm surveyForm;
    private List<String> headers;

	public SurveyActivityExtractionHeadersFactory(SurveyForm surveyForm) {
		this.surveyForm = surveyForm;
		this.headers = new LinkedList<>();
		this.buildHeadersInfo();
	}

	public List<String> getHeaders() {
		return this.headers;
	}

	private void buildHeadersInfo() {
		/* Basic info headers */
		this.headers.add(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.ACRONYM.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.MODE.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.TYPE.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.CATEGORY.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.INTERVIEWER.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.CREATION_DATE.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.PAPER_INTERVIEWER.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getValue());

		/* Answers headers */
		this.surveyForm.getSurveyTemplate().itemContainer.forEach(surveyItem -> {
			if (surveyItem instanceof Question) {
				this.headers.addAll(surveyItem.getExtractionIDs());
				this.headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX.getValue());
				this.headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX.getValue());
			}
		});
	}

}
