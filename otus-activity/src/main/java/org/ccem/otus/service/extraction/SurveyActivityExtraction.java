package org.ccem.otus.service.extraction;

import br.org.otus.api.Extractable;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.ExtractionFill;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.navigation.NavigationTrackingItem;
import org.ccem.otus.model.survey.activity.navigation.enums.NavigationTrackingItemStatuses;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.service.extraction.enums.SurveyActivityExtractionHeaders;

import java.util.*;

public class SurveyActivityExtraction implements Extractable {

	private List<SurveyActivity> surveyActivities;
	private LinkedHashMap<String, Object> basicHeaders;
	private LinkedHashMap<String, Object> questionHeaders;
	private List<List<Object>> values;

	public SurveyActivityExtraction(List<SurveyActivity> surveyActivities) {
		this.surveyActivities = surveyActivities;
		setHeaders();
	}

	private void setHeaders() {
		this.basicHeaders = new LinkedHashMap<>();
//		this.questionHeaders = new LinkedHashMap<>();

		// basic info headers
		basicHeaders.put(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName(), "");
		basicHeaders.put(SurveyActivityExtractionHeaders.ACRONYM.getName(), "");
		basicHeaders.put(SurveyActivityExtractionHeaders.CATEGORY.getName(), "");
		basicHeaders.put(SurveyActivityExtractionHeaders.INTERVIEWER.getName(), "");
		basicHeaders.put(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName(), "");
		basicHeaders.put(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName(), "");
		basicHeaders.put(SurveyActivityExtractionHeaders.CREATION_DATE.getName(), "");
		basicHeaders.put(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName(), "");
		basicHeaders.put(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName(), "");

		// answer headers
//		surveyActivities.get(0).getSurveyForm().getSurveyTemplate().itemContainer.forEach(surveyItem -> {
//			for (String header : surveyItem.getExtractionIDs()) {
//				// if () isQuestion
//				questionHeaders.put(header, "");
//			}
//			questionHeaders.put(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX, "");
//			questionHeaders.put(surveyItem.getCustomID() + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX, "");
//		});
	}

	@Override
	public Set<String> getHeaders() {
		// TODO: 03/10/17 como validar que nenhuma chave se repetiu?
		Set<String> headers = new HashSet<>();
		headers.addAll(new ArrayList<>(basicHeaders.keySet()));
//		headers.addAll(new ArrayList<>(questionHeaders.keySet()));
		return headers;
	}

	@Override
	public List<List<Object>> getValues() {
		this.values = new ArrayList<List<Object>>();
		for (SurveyActivity surveyActivity : surveyActivities) {
			LinkedHashMap<String, Object> basicInfoMap = new LinkedHashMap<>(this.basicHeaders);
//			LinkedHashMap<String, Object> questionInfoMap = new LinkedHashMap<>(this.questionHeaders);

			List<Object> basicInfo = getSurveyBasicInfo(basicInfoMap, surveyActivity);
//			List<Object> questionInfo = getSurveyQuestionInfo(questionInfoMap, surveyActivity);

			List<Object> surveyInfo = new ArrayList<>(); //concatenar ambas listas

			//isso preserva a ordem?
			surveyInfo.addAll(basicInfo);
//			surveyInfo.addAll(questionInfo);

			values.add(surveyInfo);

		}
		return values;
	}

	private List<Object> getSurveyBasicInfo(LinkedHashMap<String, Object> basicInfoMap, SurveyActivity surveyActivity) {
		basicInfoMap.replace(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getName(), surveyActivity.getParticipantData().getRecruitmentNumber());
		basicInfoMap.replace(SurveyActivityExtractionHeaders.ACRONYM.getName(), surveyActivity.getSurveyForm().getSurveyTemplate().identity.acronym);
		basicInfoMap.replace(SurveyActivityExtractionHeaders.CATEGORY.getName(), surveyActivity.getMode());
		basicInfoMap.replace(SurveyActivityExtractionHeaders.INTERVIEWER.getName(), surveyActivity.getLastInterview().getInterviewer().getEmail());
		basicInfoMap.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS.getName(), surveyActivity.getCurrentStatus().getName()); //get last
		basicInfoMap.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getName(), surveyActivity.getCurrentStatus().getDate()); // TODO: 03/10/17 test date type
		basicInfoMap.replace(SurveyActivityExtractionHeaders.CREATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.CREATED.getName()).getDate());  // TODO: 03/10/17 use enum?
		basicInfoMap.replace(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).getDate());
		basicInfoMap.replace(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getName(), surveyActivity.getLastStatusByName(ActivityStatusOptions.FINALIZED.getName()).getDate());

		return new ArrayList<>(basicInfoMap.values());
	}

	private List<Object> getSurveyQuestionInfo(LinkedHashMap<String, Object> basicInfoMap, SurveyActivity surveyActivity){
		// TODO: obter valores de repostas e adicionar a surveyMap
		// surveyMap.replace("q1", "answer");
		for (QuestionFill fill : surveyActivity.getFillContainer().getFillingList()) {
			List<NavigationTrackingItem> items = surveyActivity.getNavigationTracker().items;

			for (NavigationTrackingItem item : items) {
				if (item.equals(fill.getQuestionID())) {
					if (!item.state.equals(NavigationTrackingItemStatuses.SKIPPED)) {
						//begin item extraction - if not skipped

						ExtractionFill extration = fill.extraction();



					}
				}
			}


		}


		return null;
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
