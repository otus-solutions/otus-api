package br.org.otus.survey.services;

import br.org.otus.survey.SurveyDao;
import br.org.otus.survey.validators.CustomIdValidator;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.survey.form.SurveyForm;

import javax.ejb.Stateless;

@Stateless
public class SurveyValidatorServiceBean implements  SurveyValidatorService{

	@Override
	public void validateSurvey(SurveyDao surveyDao, SurveyForm surveyForm) throws AlreadyExistException {
		CustomIdValidator customIdValidator = new CustomIdValidator(surveyDao, surveyForm);
		if (!customIdValidator.validate().isValid()) {
			throw new AlreadyExistException(new Throwable("Item ID already exists"));
		}
	}

}