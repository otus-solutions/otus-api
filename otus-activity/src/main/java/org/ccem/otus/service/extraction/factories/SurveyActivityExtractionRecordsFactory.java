package org.ccem.otus.service.extraction.factories;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.ExtractionFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.interview.Interview;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.service.extraction.enums.ExtractionVariables;
import org.ccem.otus.service.extraction.enums.SurveyActivityExtractionHeaders;
import org.ccem.otus.survey.template.item.SurveyItem;

public class SurveyActivityExtractionRecordsFactory {

	private LinkedHashMap<String, Object> surveyInformation;

	public SurveyActivityExtractionRecordsFactory() {
		this.surveyInformation = new LinkedHashMap<String, Object>();
	}

	public void getSurveyBasicInfo(SurveyActivity surveyActivity) {
		this.getSurveyInformation().replace(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getValue(),
				surveyActivity.getParticipantData().getRecruitmentNumber());
		this.getSurveyInformation()
				.replace(SurveyActivityExtractionHeaders.ACRONYM.getValue(), surveyActivity.getSurveyForm().getSurveyTemplate().identity.acronym);
		this.getSurveyInformation().replace(SurveyActivityExtractionHeaders.CATEGORY.getValue(), surveyActivity.getMode());

		final Interview lastInterview = surveyActivity.getLastInterview().orElse(null);
		final String interviewerEmail = lastInterview != null ? lastInterview.getInterviewer().getEmail() : null;
		this.getSurveyInformation().replace(SurveyActivityExtractionHeaders.INTERVIEWER.getValue(), interviewerEmail);

		final ActivityStatus currentActivityStatus = surveyActivity.getCurrentStatus().orElse(null);
		final String currentStatus = currentActivityStatus != null ? currentActivityStatus.getName() : null;
		this.getSurveyInformation().replace(SurveyActivityExtractionHeaders.CURRENT_STATUS.getValue(), currentStatus);

		final LocalDateTime currentStatusDate = (currentActivityStatus != null) ? currentActivityStatus.getDate() : null;
		this.getSurveyInformation().replace(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getValue(), currentStatusDate);

		final ActivityStatus creationStatus = surveyActivity.getLastStatusByName(ActivityStatusOptions.CREATED.getName()).orElse(null);
		final LocalDateTime creationTime = (creationStatus != null) ? creationStatus.getDate() : null;
		this.getSurveyInformation().replace(SurveyActivityExtractionHeaders.CREATION_DATE.getValue(), creationTime);

		final ActivityStatus paperStatus = surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).orElse(null);
		final LocalDateTime paperRealizationDate = (paperStatus != null) ? paperStatus.getDate() : null;
		this.getSurveyInformation().replace(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getValue(), paperRealizationDate);

		final String paperInterviewer = (paperStatus != null) ? paperStatus.getUser().getEmail() : null;
		this.getSurveyInformation().replace(SurveyActivityExtractionHeaders.PAPER_INTERVIEWER.getValue(), paperInterviewer);

		final ActivityStatus finalizedStatus = surveyActivity.getLastStatusByName(ActivityStatusOptions.FINALIZED.getName()).orElse(null);
		final LocalDateTime lastFinalizationDate = (finalizedStatus != null) ? finalizedStatus.getDate() : null;
		this.getSurveyInformation().replace(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getValue(), lastFinalizationDate);
	}

	public void getSurveyQuestionInfo(SurveyActivity surveyActivity) throws DataNotFoundException {
		final Map<String, String> customIDMap = surveyActivity.getSurveyForm().getSurveyTemplate().mapTemplateAndCustomIDS();

		for (NavigationTrackingItem trackingItem : surveyActivity.getNavigationTracker().items) {

			final String itemCustomID = customIDMap.get(trackingItem.id);

			switch (trackingItem.state) {
			// TODO: 11/10/17 apply enum: NavigationTrackingItemStatuses
			case "SKIPPED": {
				// TODO: 16/10/17 create ExtractionExceptions
				SurveyItem surveyItem = surveyActivity.getSurveyForm().getSurveyTemplate().findSurveyItem(trackingItem.id).orElseThrow(RuntimeException::new);
				skippAnswer(surveyItem.getExtractionIDs());
				break;
			}
			case "ANSWERED": {
				QuestionFill questionFill = surveyActivity.getFillContainer().getQuestionFill(trackingItem.id).orElseThrow(DataNotFoundException::new);
				ExtractionFill extraction = questionFill.extraction();
				fillQuestionInfo(customIDMap, extraction);
				break;
			}
			case "NOT_VISITED":
			case "IGNORED":
			case "VISITED":{
				QuestionFill questionFill = surveyActivity.getFillContainer().getQuestionFill(trackingItem.id).orElse(null);
				if (questionFill != null){
					ExtractionFill extraction = questionFill.extraction();
					fillQuestionInfo(customIDMap, extraction);
				}
				break;
			}
			default:{ // TODO: 17/10/17 check other possible cases
				break;
			}
			}
		}
	}

	private void fillQuestionInfo(Map<String, String> customIDMap, ExtractionFill filler) {
		final String answerCustomID = customIDMap.get(filler.getQuestionID());

		for (Map.Entry<String, Object> pair : filler.getAnswerExtract().entrySet()) {
			String key = pair.getKey();
			this.getSurveyInformation().replace(customIDMap.get(key), pair.getValue());
		}

		this.getSurveyInformation().replace(answerCustomID + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX, filler.getMetadata());
		this.getSurveyInformation().replace(answerCustomID + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX, filler.getComment());

	}

	private void skippAnswer(List<String> extractionIDs) {
		for (String extractionID : extractionIDs) {
			this.getSurveyInformation().replace(extractionID, ExtractionVariables.POINT_P.getValue());
		}
	}

	public LinkedHashMap<String, Object> getSurveyInformation() {
		return surveyInformation;
	}

}
