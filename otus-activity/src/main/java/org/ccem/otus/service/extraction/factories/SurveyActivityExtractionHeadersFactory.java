package org.ccem.otus.service.extraction.factories;

import java.util.LinkedHashSet;
import java.util.List;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.enums.SurveyActivityExtractionHeaders;
import org.ccem.otus.survey.template.item.questions.Question;

public class SurveyActivityExtractionHeadersFactory {

	private List<SurveyActivity> surveyActivities;
	private LinkedHashSet<String> headers;

	public SurveyActivityExtractionHeadersFactory(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
		this.headers = new LinkedHashSet<String>();
	}

	public LinkedHashSet<String> getHeaders() {
		this.buildHeadersInfo();
		return this.headers;
	}

	private void buildHeadersInfo() {
		/* Basic info headers */
		this.headers.add(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.ACRONYM.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.CATEGORY.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.TYPE.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.INTERVIEWER.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.CREATION_DATE.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.PAPER_INTERVIEWER.getValue());
		this.headers.add(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getValue());

		/* Answers headers */
		this.surveyActivities.get(0).getSurveyForm().getSurveyTemplate().itemContainer.forEach(surveyItem -> {
			if (surveyItem instanceof Question) {
				for (String header : surveyItem.getExtractionIDs()) {
					this.headers.add(header);
				}
				this.headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX.getValue());
				this.headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX.getValue());
			}
		});
	}

}
