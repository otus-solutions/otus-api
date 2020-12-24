package br.org.otus.survey.services;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import java.text.ParseException;

public interface ActivityTasksService {

  SurveyActivity updateActivity(SurveyActivity surveyActivity, String token) throws ParseException, DataNotFoundException;

}
