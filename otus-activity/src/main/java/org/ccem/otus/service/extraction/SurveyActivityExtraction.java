package org.ccem.otus.service.extraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.ExtractionFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.navigation.enums.NavigationTrackingItemStatuses;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.service.extraction.enums.SurveyActivityExtractionHeaders;
import org.ccem.otus.survey.template.item.questions.Question;

import br.org.otus.api.Extractable;

public class SurveyActivityExtraction implements Extractable {

	private List<SurveyActivity> surveyActivities;
	private LinkedHashSet<String> headers;
	private LinkedHashMap<String, Object> surveyInformation;

	public SurveyActivityExtraction(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
		this.headers = new LinkedHashSet<String>();
		this.surveyInformation = new LinkedHashMap<String, Object>();
	}

	@Override
	public LinkedHashSet<String> getHeaders() {
		this.buildHeadersInfo();
		return this.headers;
	}

	@Override
	public List<List<Object>> getValues() {
		List<List<Object>> values = new ArrayList<List<Object>>();
		for (SurveyActivity surveyActivity : surveyActivities) {
			List<Object> resultInformation = new ArrayList<>();

			Iterator<String> iterator = this.headers.iterator();
			while (iterator.hasNext()) {
				surveyInformation.put(iterator.next(), "");
			}
			this.getSurveyBasicInfo(surveyActivity);
			this.getSurveyQuestionInfo(surveyActivity);
			resultInformation.addAll(new ArrayList<>(this.surveyInformation.values()));
			values.add(resultInformation);
		}
		return values;
	}

	private void buildHeadersInfo() {
		this.headers.add(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName());
		this.headers.add(SurveyActivityExtractionHeaders.ACRONYM.getName());
		this.headers.add(SurveyActivityExtractionHeaders.CATEGORY.getName());
		this.headers.add(SurveyActivityExtractionHeaders.INTERVIEWER.getName());
		this.headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName());
		this.headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName());
		this.headers.add(SurveyActivityExtractionHeaders.CREATION_DATE.getName());
		this.headers.add(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName());
		this.headers.add(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName());

		/* Activities headers */
		surveyActivities.get(0).getSurveyForm().getSurveyTemplate().itemContainer.forEach(surveyItem -> {
			for (String header : surveyItem.getExtractionIDs()) {
				if (surveyItem instanceof Question) {
					this.headers.add(header);
					this.headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX);
					this.headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX);
				}
			}
		});
	}

	private void getSurveyBasicInfo(SurveyActivity surveyActivity) {
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName(), surveyActivity.getParticipantData().getRecruitmentNumber());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.ACRONYM.getName(), surveyActivity.getSurveyForm().getSurveyTemplate().identity.acronym);
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.CATEGORY.getName(), surveyActivity.getMode());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.INTERVIEWER.getName(), surveyActivity.getLastInterview().getInterviewer().getEmail());
		// get last
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName(), surveyActivity.getCurrentStatus().getName());
		// TODO: 03/10/17 test date type
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName(), surveyActivity.getCurrentStatus().getDate());
		// TODO: 03/10/17 use enum?
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.CREATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.CREATED.getName()).getDate());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).getDate());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.FINALIZED.getName()).getDate());
	}

	private void getSurveyQuestionInfo(SurveyActivity surveyActivity) {
		for (QuestionFill questionFill : surveyActivity.getFillContainer().getFillingList()) {
			List<NavigationTrackingItem> trackingItems = surveyActivity.getNavigationTracker().items;
			for (NavigationTrackingItem navigationTrackingItem : trackingItems) {
				if (navigationTrackingItem.id.equals(questionFill.getQuestionID())) {
					if (!navigationTrackingItem.state.equals(NavigationTrackingItemStatuses.SKIPPED)) {
						// begin extraction - if not skipped
						ExtractionFill extraction = questionFill.extraction();
						fillQuestionInfo(extraction);
					}
				}
			}
		}
	}

	private void fillQuestionInfo(ExtractionFill filler) {
		for (Map.Entry<String, Object> pair : filler.getAnswerExtract().entrySet()) {
			String key = pair.getKey();
			this.surveyInformation.replace(key, pair.getValue());
		}
		this.surveyInformation.replace(filler.getQuestionID() + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX, filler.getComment());
		this.surveyInformation.replace(filler.getQuestionID() + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX, filler.getMetadata());
	}
}
