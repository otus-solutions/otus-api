package org.ccem.otus.service.extraction;

import br.org.otus.api.Extractable;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.ExtractionFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.navigation.enums.NavigationTrackingItemStatuses;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.service.extraction.enums.SurveyActivityExtractionHeaders;

import java.util.*;

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
		headers.put(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName(), "");
		headers.put(SurveyActivityExtractionHeaders.ACRONYM.getName(), "");
		headers.put(SurveyActivityExtractionHeaders.CATEGORY.getName(), "");
		headers.put(SurveyActivityExtractionHeaders.TYPE.getName(), "");
		headers.put(SurveyActivityExtractionHeaders.INTERVIEWER.getName(), "");
		headers.put(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName(), "");
		headers.put(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName(), "");
		headers.put(SurveyActivityExtractionHeaders.CREATION_DATE.getName(), "");
		headers.put(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName(), "");
		headers.put(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName(), "");

		// answer headers
		surveyActivities.get(0).getSurveyForm().getSurveyTemplate().itemContainer.forEach(surveyItem -> {
			for (String header : surveyItem.getExtractionIDs()) {
				// if () isQuestion
				headers.put(header, "");
			}
			headers.put(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX, "");
			headers.put(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX, "");
		});
	}

	@Override
	public Set<String> getHeaders() {
		return headers.keySet();
	}

	@Override
	public List<List<Object>> getValues() {
		this.values = new ArrayList<List<Object>>();
		for (SurveyActivity surveyActivity : surveyActivities) {
			LinkedHashMap<String, Object> surveyMap = new LinkedHashMap<>(this.headers);
			List<Object> basicInfo = new ArrayList<>(); //setBasics(surveyMap, surveyActivity);
			List<Object> questionInfo = new ArrayList<>();

			for (QuestionFill fill : surveyActivity.getFillContainer().getFillingList()) {
				List<NavigationTrackingItem> items = surveyActivity.getNavigationTracker().items;

				for (NavigationTrackingItem item : items) {
					if (item.equals(fill.getQuestionID())) {
						if (!item.state.equals(NavigationTrackingItemStatuses.SKIPPED)) {
							//begin item extraction - if not skipped

							ExtractionFill extration = fill.extration();



						}
					}
				}


			}

			// TODO: obter valores de repostas e adicionar a surveyMap
			// surveyMap.replace("q1", "answer");
			// this.values.addAll(surveyMap.values());
			// this.values.
		}
		return values;
	}

	private void setBasics(LinkedHashMap<String, Object> headersMap, SurveyActivity surveyActivity) {
		headersMap.replace(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName(), surveyActivity.getParticipantData().getRecruitmentNumber());
		headersMap.replace(SurveyActivityExtractionHeaders.ACRONYM.getName(), surveyActivity.getSurveyForm().getSurveyTemplate().identity.acronym);
		headersMap.replace(SurveyActivityExtractionHeaders.CATEGORY.getName(), surveyActivity);
		headersMap.replace(SurveyActivityExtractionHeaders.TYPE.getName(), surveyActivity);
		headersMap.replace(SurveyActivityExtractionHeaders.INTERVIEWER.getName(), surveyActivity);

		final List<ActivityStatus> statusHistory = surveyActivity.getStatusHistory();

		headersMap.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName(), statusHistory.get(statusHistory.size()-1).getName()); //get last
		headersMap.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName(), statusHistory.get(statusHistory.size()-1).getDate()); // TODO: 03/10/17 test type
		headersMap.replace(SurveyActivityExtractionHeaders.CREATION_DATE.getName(), surveyActivity);
		headersMap.replace(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName(), surveyActivity);
		headersMap.replace(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName(), surveyActivity);
	}

	private void fillQuestionInfo(LinkedHashMap map, ExtractionFill filler){
		Map<String, Object> answerExtract = filler.getAnswerExtract();
//		map.replaceAll(answerExtract);
		for (Map.Entry<String, Object> pair : filler.getAnswerExtract().entrySet()) {
			String key = pair.getKey();
			map.replace(key, pair.getValue());
		}

	}
}
