package br.org.otus.survey.services;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;

import java.text.ParseException;

public interface ActivityTasksService {

  String create(SurveyActivity surveyActivity, boolean notify);

  SurveyActivity updateActivity(SurveyActivity surveyActivity, String token) throws ParseException, DataNotFoundException;

  void save(String userEmail, OfflineActivityCollection offlineActivityCollection) throws DataNotFoundException;

  void discardById(String activityId) throws DataNotFoundException;

}
