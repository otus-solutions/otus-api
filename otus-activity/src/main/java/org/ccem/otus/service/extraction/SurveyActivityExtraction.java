package org.ccem.otus.service.extraction;

import br.org.otus.api.Extractable;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.ExtractionFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.service.extraction.enums.SurveyActivityExtractionHeaders;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.ccem.otus.survey.template.item.questions.Question;

import java.util.*;

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
	public List<List<Object>> getValues() throws DataNotFoundException {
		List<List<Object>> values = new ArrayList<>();
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
		/* Basic info headers */
		this.headers.add(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName());
		this.headers.add(SurveyActivityExtractionHeaders.ACRONYM.getName());
		this.headers.add(SurveyActivityExtractionHeaders.CATEGORY.getName());
		this.headers.add(SurveyActivityExtractionHeaders.INTERVIEWER.getName());
		this.headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName());
		this.headers.add(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName());
		this.headers.add(SurveyActivityExtractionHeaders.CREATION_DATE.getName());
		this.headers.add(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName());
		this.headers.add(SurveyActivityExtractionHeaders.PAPER_INTERVIEWER.getName());
		this.headers.add(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName());

		/* Answers headers */
		surveyActivities.get(0).getSurveyForm().getSurveyTemplate().itemContainer.forEach(surveyItem -> {
			if (surveyItem instanceof Question) {
				for (String header : surveyItem.getExtractionIDs()) {
						this.headers.add(header);
				}
			}
			this.headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX);
			this.headers.add(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX);
		});
	}

	private void getSurveyBasicInfo(SurveyActivity surveyActivity) {
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName(), surveyActivity.getParticipantData().getRecruitmentNumber());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.ACRONYM.getName(), surveyActivity.getSurveyForm().getSurveyTemplate().identity.acronym);
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.CATEGORY.getName(), surveyActivity.getMode());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.INTERVIEWER.getName(), surveyActivity.getLastInterview().getInterviewer().getEmail());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName(), surveyActivity.getCurrentStatus().getName());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName(), surveyActivity.getCurrentStatus().getDate());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.CREATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.CREATED.getName()).getDate());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).getDate());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.PAPER_INTERVIEWER.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).getUser().getEmail());
		this.surveyInformation.replace(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.FINALIZED.getName()).getDate());
	}

	private void getSurveyQuestionInfo(SurveyActivity surveyActivity) throws DataNotFoundException {
		final Map<String, String> customIDMap = surveyActivity.getSurveyForm().getSurveyTemplate().mapTemplateAndCustomIDS();

		for (NavigationTrackingItem trackingItem : surveyActivity.getNavigationTracker().items) {

			final String itemCustomID = customIDMap.get(trackingItem.id);

			switch (trackingItem.state){
				// TODO: 11/10/17 apply enum: NavigationTrackingItemStatuses
				case "ANSWERED":{
					QuestionFill questionFill = surveyActivity.getFillContainer().getQuestionFill(trackingItem.id);
					ExtractionFill extraction = questionFill.extraction();
					fillQuestionInfo(customIDMap, extraction);
					break;
				}
				case "SKIPPED":{
					SurveyItem surveyItem = surveyActivity.getSurveyForm().getSurveyTemplate().findSurveyItem(trackingItem.id).orElseThrow(() -> new RuntimeException());// TODO: 16/10/17 create ExtractionExceptions
					skippAnswer(surveyItem.getExtractionIDs());
					break;
				}
				// TODO: 11/10/17 other states
			}
		}
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

	private void skippAnswer(List<String> extractionIDs){
		// TODO: 11/10/17 skip on all customIDs (checkbox, grid)
		for (String extractionID : extractionIDs) {
			this.surveyInformation.replace(extractionID, ".p");
		}
	}
}
