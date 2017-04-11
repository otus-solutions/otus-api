package br.org.otus.survey.services;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.survey.form.SurveyForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.survey.SurveyDao;
import br.org.otus.survey.validators.AcronymValidator;
import br.org.otus.survey.validators.CustomIdValidator;
import br.org.otus.survey.validators.ValidatorResponse;

@PrepareForTest({ SurveyDao.class, SurveyForm.class, SurveyValidorService.class })
@RunWith(PowerMockRunner.class)
public class SurveyValidorServiceTest {

	@InjectMocks
	private SurveyValidorService service;

	@Mock
	SurveyDao surveyDaoMock;

	@Mock
	SurveyForm surveyFormMock;

	@Mock
	AcronymValidator acronymValidatorMock;

	@Mock
	CustomIdValidator customIdValidatorMock;

	@Mock
	ValidatorResponse acronymValidatorResponseMock;
	@Mock
	ValidatorResponse customIdValidatorResponseMock;

	@Test(expected = AlreadyExistException.class)
	public void acronymValidator_should_throw_AlreadyExistException_case_Acronym_already_exist() throws Exception {
		isValidAcronym(false);
		service.validateSurvey(surveyDaoMock, surveyFormMock);
	}

	@Test(expected = AlreadyExistException.class)
	public void customIdValidator_should_throw_AlreadyExistException_case_Item_ID_already_exist() throws Exception {
		isValidAcronym(true);
		isValidCustomID(false);

		service.validateSurvey(surveyDaoMock, surveyFormMock);
	}

	@Test
	public void validateSurvey_call_validators_but_not_throws_exceptions() throws Exception {
		isValidAcronym(true);
		isValidCustomID(true);

		service.validateSurvey(surveyDaoMock, surveyFormMock);

		Mockito.verify(acronymValidatorMock).validate();
		Mockito.verify(acronymValidatorMock.validate()).isValid();
	}

	private void isValidAcronym(boolean valid) throws Exception {
		whenNew(AcronymValidator.class).withArguments(surveyDaoMock, surveyFormMock).thenReturn(acronymValidatorMock);
		when(acronymValidatorMock.validate()).thenReturn(acronymValidatorResponseMock);
		when(acronymValidatorResponseMock.isValid()).thenReturn(valid);
	}

	private void isValidCustomID(boolean valid) throws Exception {
		whenNew(CustomIdValidator.class).withArguments(surveyDaoMock, surveyFormMock).thenReturn(customIdValidatorMock);
		when(customIdValidatorMock.validate()).thenReturn(customIdValidatorResponseMock);
		when(customIdValidatorResponseMock.isValid()).thenReturn(valid);
	}

}
