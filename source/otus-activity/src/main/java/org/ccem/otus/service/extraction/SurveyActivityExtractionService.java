package org.ccem.otus.service.extraction;

import java.util.List;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.preprocessing.ActivityPreProcessor;
import org.ccem.otus.survey.form.SurveyForm;

public interface SurveyActivityExtractionService {

  void createExtraction(SurveyForm surveyForm, List<SurveyActivity> surveyActivities);

  void addPreProcessor(ActivityPreProcessor activityPreProcessor);

}
