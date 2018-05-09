package br.org.otus.survey.services;

import br.org.otus.survey.SurveyDaoBean;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.survey.form.SurveyForm;

public interface SurveyValidatorService {
    void validateSurvey(SurveyDaoBean surveyDaoBean, SurveyForm surveyForm) throws AlreadyExistException;
}
