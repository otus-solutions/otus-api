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
		this.buildHeaders();
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
			resultInformation.addAll(this.getSurveyBasicInfo(surveyActivity));
			resultInformation.addAll(this.getSurveyQuestionInfo(surveyActivity));

			values.add(resultInformation);
		}
		return values;
	}

	private void buildHeaders() {
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

	private List<Object> getSurveyBasicInfo(SurveyActivity surveyActivity) {	
		this.surveyInformation.put(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName(), surveyActivity.getParticipantData().getRecruitmentNumber());
		this.surveyInformation.put(SurveyActivityExtractionHeaders.ACRONYM.getName(), surveyActivity.getSurveyForm().getSurveyTemplate().identity.acronym);
		this.surveyInformation.put(SurveyActivityExtractionHeaders.CATEGORY.getName(), surveyActivity.getMode());
		this.surveyInformation.put(SurveyActivityExtractionHeaders.INTERVIEWER.getName(), surveyActivity.getLastInterview().getInterviewer().getEmail());
		this.surveyInformation.put(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName(), surveyActivity.getCurrentStatus().getName());
		// TODO: 03/10/17 test date type
		this.surveyInformation.put(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName(), surveyActivity.getCurrentStatus().getDate());
		// TODO: 03/10/17 use enum?
		this.surveyInformation.put(SurveyActivityExtractionHeaders.CREATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.CREATED.getName()).getDate());
		this.surveyInformation.put(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).getDate());
		this.surveyInformation.put(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.FINALIZED.getName()).getDate());

		return new ArrayList<>(surveyInformation.values());
	}

	private List<Object> getSurveyQuestionInfo(SurveyActivity surveyActivity) {
		final Map<String, String> customIDMap = surveyActivity.getSurveyForm().getSurveyTemplate().mapTemplateAndCustomIDS();
		final List<QuestionFill> fillingList = surveyActivity.getFillContainer().getFillingList();

		for (NavigationTrackingItem trackingItem : surveyActivity.getNavigationTracker().items) {
			// TODO: 11/10/17 apply enum: NavigationTrackingItemStatuses

			final String itemCustomID = customIDMap.get(trackingItem.id);

			switch (trackingItem.state){
				case "ANSWERED":{
					QuestionFill questionFill = surveyActivity.getFillContainer().getQuestionFill(trackingItem.id);
					ExtractionFill extraction = questionFill.extraction();
					fillQuestionInfo(customIDMap, extraction);
					break;
				}
				case "SKIPPED":{
					// TODO: 11/10/17 fill with .p value
					skippAnswer(itemCustomID);
					break;
				}
				// TODO: 11/10/17 other states

			}
		}
		return new ArrayList<>(surveyInformation.values());
	}

	private void fillQuestionInfo(Map<String, String> customIDMap, ExtractionFill filler) {
		final String answerCustomID = customIDMap.get(filler.getQuestionID());

		for (Map.Entry<String, Object> pair : filler.getAnswerExtract().entrySet()) {
			String key = pair.getKey();
			this.surveyInformation.replace(customIDMap.get(key), pair.getValue());
		}

		this.surveyInformation.replace(answerCustomID + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX, filler.getComment());
		this.surveyInformation.replace(answerCustomID + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX, filler.getMetadata());

	}

	private void skippAnswer(String customID){
		// TODO: 11/10/17 skip on all customIDs (checkbox, grid)

	}
}
