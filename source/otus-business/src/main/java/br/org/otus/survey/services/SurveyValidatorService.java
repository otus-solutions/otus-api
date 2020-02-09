package br.org.otus.survey.services;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.persistence.SurveyDao;
import org.ccem.otus.survey.form.SurveyForm;

public interface SurveyValidatorService {

  void validateSurvey(SurveyDao surveyDao, SurveyForm surveyForm) throws AlreadyExistException;
}
