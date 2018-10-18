package org.ccem.otus.service.extraction.factories;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.ccem.otus.survey.template.item.questions.Question;
import org.ccem.otus.survey.template.item.questions.metadata.MetadataOption;
import org.ccem.otus.survey.template.item.questions.selectable.SingleSelectionQuestion;
import org.ccem.otus.survey.template.item.questions.selectable.options.RadioOption;

public class SurveyActivityExtractionRecordsFactory {

  private LinkedHashMap<String, Object> surveyInformation;
  private SurveyForm surveyForm;

  public SurveyActivityExtractionRecordsFactory(SurveyForm surveyForm, LinkedHashSet<String> headers) {
    this.surveyInformation = new LinkedHashMap<>();
    this.surveyForm = surveyForm;
    for (Object header : headers) {
      this.surveyInformation.put(header.toString(), "");
    }
  }

  public LinkedHashMap<String, Object> getSurveyInformation() {
    return this.surveyInformation;
  }

  public void getSurveyBasicInfo(SurveyActivity surveyActivity) {
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.RECRUITMENT_NUMBER.getValue(), SurveyBasicInfoRecordsFactory.getRecruitmentNumber(surveyActivity));
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.ACRONYM.getValue(), this.surveyForm.getSurveyTemplate().identity.acronym);
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.MODE.getValue(), surveyActivity.getMode());
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.CATEGORY.getValue(), SurveyBasicInfoRecordsFactory.getCategory(surveyActivity));

    final String interviewerEmail = SurveyBasicInfoRecordsFactory.getLastInterviewer(surveyActivity);
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.INTERVIEWER.getValue(), interviewerEmail);

    final ActivityStatus currentActivityStatus = surveyActivity.getCurrentStatus().orElse(null);

    final String currentStatus = SurveyBasicInfoRecordsFactory.getCurrentStatusName(currentActivityStatus);
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS.getValue(), currentStatus);

    final LocalDateTime currentStatusDate = SurveyBasicInfoRecordsFactory.getCurrentStatusDate(currentActivityStatus);
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.CURRENT_STATUS_DATE.getValue(), currentStatusDate);

    final LocalDateTime creationTime = SurveyBasicInfoRecordsFactory.getCreationDate(surveyActivity);
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.CREATION_DATE.getValue(), creationTime);

    final ActivityStatus paperStatus = surveyActivity.getLastStatusByName(ActivityStatusOptions.INITIALIZED_OFFLINE.getName()).orElse(null);
    final LocalDateTime paperRealizationDate = SurveyBasicInfoRecordsFactory.getPaperRealizationDate(paperStatus);
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.PAPER_REALIZATION_DATE.getValue(), paperRealizationDate);

    final String paperInterviewer = SurveyBasicInfoRecordsFactory.getPaperInterviewer(paperStatus);
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.PAPER_INTERVIEWER.getValue(), paperInterviewer);

    final LocalDateTime lastFinalizationDate = SurveyBasicInfoRecordsFactory.getLasFinalizationDate(surveyActivity);
    this.surveyInformation.replace(SurveyActivityExtractionHeaders.LAST_FINALIZATION_DATE.getValue(), lastFinalizationDate);
  }

  public void getSurveyQuestionInfo(SurveyActivity surveyActivity) throws DataNotFoundException {
    final Map<String, String> customIDMap = this.surveyForm.getSurveyTemplate().mapTemplateAndCustomIDS();

    for (NavigationTrackingItem trackingItem : surveyActivity.getNavigationTracker().items) {
      // TODO: 16/10/17 create ExtractionExceptions
      SurveyItem surveyItem = this.surveyForm.getSurveyTemplate().findSurveyItem(trackingItem.id).orElseThrow(RuntimeException::new);

      switch (trackingItem.state) {
        // TODO: 11/10/17 apply enum: NavigationTrackingItemStatuses
        case "SKIPPED": {
          skippAnswer(surveyItem.getCustomID());
          break;
        }
        case "ANSWERED": {
          // TODO: 16/10/17 create ExtractionExceptions
          QuestionFill questionFill = surveyActivity.getFillContainer().getQuestionFill(trackingItem.id).orElseThrow(DataNotFoundException::new);
          ExtractionFill extraction = questionFill.extraction();
          fillAnswerInfo(customIDMap, extraction, surveyItem);
          break;
        }
        case "NOT_VISITED":
        case "IGNORED":
        case "VISITED": {
          QuestionFill questionFill = surveyActivity.getFillContainer().getQuestionFill(trackingItem.id).orElse(null);
          if (questionFill != null) {
            ExtractionFill extraction = questionFill.extraction();
            fillAnswerInfo(customIDMap, extraction, surveyItem);
          }
          break;
        }
      }
    }
  }

  private void fillAnswerInfo(Map<String, String> customIDMap, ExtractionFill filler, SurveyItem surveyItem) throws DataNotFoundException {
    final String questionID = filler.getQuestionID();
    final String answerCustomID = customIDMap.get(questionID);

    for (Map.Entry<String, Object> pair : filler.getAnswerExtract().entrySet()) {
      String key = pair.getKey();

      //TODO CheckboxQuestion Option ID FIX
      if (surveyItem.objectType.equals("CheckboxQuestion") ||
        surveyItem.objectType.equals("GridTextQuestion") ||
        surveyItem.objectType.equals("GridIntegerQuestion")) {
        this.surveyInformation.replace(key, pair.getValue());
      } else {

        //TODO 17/10/18: check whan getValue comes null
        if (surveyItem.objectType.equals("SingleSelectionQuestion") && pair.getValue() != null) {
          this.surveyInformation.replace(customIDMap.get(key), sinleSelectionFix(surveyItem, Integer.parseInt(pair.getValue().toString())));
        } else {
          this.surveyInformation.replace(customIDMap.get(key), pair.getValue());

        }

      }
    }

    if (filler.getMetadata() != null) {
      final MetadataOption metadataExtractionValue = ((Question) surveyItem).metadata.getMetadataByValue(Integer.valueOf(filler.getMetadata())).orElseThrow(DataNotFoundException::new);
      this.surveyInformation.replace(answerCustomID + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX, metadataExtractionValue.extractionValue);
    }
    this.surveyInformation.replace(answerCustomID + SurveyActivityExtractionHeaders.QUESTION_COMMENT_SUFFIX, filler.getComment());
  }

  private void skippAnswer(String questionID) {
    this.surveyInformation.replace(questionID + SurveyActivityExtractionHeaders.QUESTION_METADATA_SUFFIX, ExtractionVariables.SKIPPED_ANSWER.getValue());
  }

  private String sinleSelectionFix(SurveyItem surveyItem, Integer value) {

    RadioOption radioOption1 = ((SingleSelectionQuestion) surveyItem).options.stream().filter(radioOption -> radioOption.value.equals(value)).findFirst().orElseThrow(RuntimeException::new);
    return radioOption1.extractionValue;
  }

}
