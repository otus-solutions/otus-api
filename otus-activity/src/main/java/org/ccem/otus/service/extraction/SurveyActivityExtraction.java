package org.ccem.otus.service.extraction;

import java.util.ArrayList;
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

	public SurveyActivityExtraction(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
		this.headers = new LinkedHashSet<String>();
	}

	@Override
	public LinkedHashSet<String> getHeaders() {
		this.buildHeaders();
		return this.headers;
	}

	@Override
	public List<List<Object>> getValues() {
		List<List<Object>> values = new ArrayList<List<Object>>();
		for (SurveyActivity surveyActivity : surveyActivities) {
			List<Object> resultInformation = new ArrayList<>();
			LinkedHashMap<String, Object> surveyInformationBasic = new LinkedHashMap<String, Object>();
			LinkedHashMap<String, Object> surveyInformationQuestion = new LinkedHashMap<String, Object>();
			resultInformation.addAll(this.getSurveyBasicInfo(surveyInformationBasic, surveyActivity));
			resultInformation.addAll(this.getSurveyQuestionInfo(surveyInformationQuestion, surveyActivity));
			values.add(resultInformation);
		}
		return values;
	}

	private void buildHeaders() {
		headers.add(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName());
		headers.add(SurveyActivityExtractionHeaders.ACRONYM.getName());
		headers.add(SurveyActivityExtractionHeaders.CATEGORY.getName());
		headers.add(SurveyActivityExtractionHeaders.INTERVIEWER.getName());
		headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName());
		headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName());
		headers.add(SurveyActivityExtractionHeaders.CREATION_DATE.getName());
		headers.add(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName());
		headers.add(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName());

		/* Activities headers */
		surveyActivities.get(0).getSurveyForm().getSurveyTemplate().itemContainer.forEach(surveyItem -> {
			for (String header : surveyItem.getExtractionIDs()) {
				if (surveyItem instanceof Question) {
					headers.add(header);
					headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX);
					headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX);
				}
			}
		});
	}

	private List<Object> getSurveyBasicInfo(LinkedHashMap<String, Object> surveyInformation, SurveyActivity surveyActivity) {
		surveyInformation.put(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName(), surveyActivity.getParticipantData().getRecruitmentNumber());
		surveyInformation.put(SurveyActivityExtractionHeaders.ACRONYM.getName(), surveyActivity.getSurveyForm().getSurveyTemplate().identity.acronym);
		surveyInformation.put(SurveyActivityExtractionHeaders.CATEGORY.getName(), surveyActivity.getMode());
		surveyInformation.put(SurveyActivityExtractionHeaders.INTERVIEWER.getName(), surveyActivity.getLastInterview().getInterviewer().getEmail());
		//get last
		surveyInformation.put(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName(), surveyActivity.getCurrentStatus().getName());
		// TODO: 03/10/17 test date type
		surveyInformation.put(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName(), surveyActivity.getCurrentStatus().getDate()); 
		// TODO: 03/10/17 use enum?
		surveyInformation.put(SurveyActivityExtractionHeaders.CREATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.CREATED.getName()).getDate());  
		surveyInformation.put(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).getDate());
		surveyInformation.put(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.FINALIZED.getName()).getDate());

		return new ArrayList<>(surveyInformation.values());
	}

	private List<Object> getSurveyQuestionInfo(LinkedHashMap<String, Object> surveyInformation, SurveyActivity surveyActivity) {
		for (QuestionFill questionFill : surveyActivity.getFillContainer().getFillingList()) {
			List<NavigationTrackingItem> trackingItems = surveyActivity.getNavigationTracker().items;
			for (NavigationTrackingItem navigationTrackingItem : trackingItems) {
				if (navigationTrackingItem.id.equals(questionFill.getQuestionID())) {
					if (!navigationTrackingItem.state.equals(NavigationTrackingItemStatuses.SKIPPED)) {
						// begin extraction - if not skipped
						ExtractionFill extraction = questionFill.extraction();
						fillQuestionInfo(surveyInformation, extraction);
					}
				}
			}
		}
		return new ArrayList<>(surveyInformation.values());
	}

	private void fillQuestionInfo(LinkedHashMap<String, Object> surveyInformation, ExtractionFill filler) {
		for (Map.Entry<String, Object> pair : filler.getAnswerExtract().entrySet()) {
			String key = pair.getKey();
			surveyInformation.replace(key, pair.getValue());
		}
		surveyInformation.replace(filler.getQuestionID() + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX, filler.getComment());
		surveyInformation.replace(filler.getQuestionID() + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX, filler.getMetadata());

	}

}
