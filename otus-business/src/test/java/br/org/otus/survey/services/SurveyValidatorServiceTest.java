package br.org.otus.survey.services;

import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.survey.form.SurveyForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.survey.SurveyDao;
import br.org.otus.survey.validators.AcronymValidator;
import br.org.otus.survey.validators.CustomIdValidator;
import br.org.otus.survey.validators.ValidatorResponse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SurveyDao.class, SurveyForm.class, SurveyValidatorService.class })
public class SurveyValidatorServiceTest {
	private static final Boolean POSITIVE_ANSWER = true;
	private static final Boolean NEGATIVE_ANSWER = false;
	private SurveyValidatorService surveyValidatorService;
	@Mock
	private SurveyDao surveyDao;
	@Mock
	private SurveyForm surveyForm;
	@Mock
	private AcronymValidator acronymValidator;
	@Mock
	private CustomIdValidator customIdValidator;
	@Mock
	private ValidatorResponse acronymValidatorResponse;
	@Mock
	private ValidatorResponse customIdValidatorResponse;
	@Mock
	private ValidatorResponse validatorResponse;

	@Before
	public void setUp() throws Exception {
		surveyValidatorService = spy(new SurveyValidatorService());
		whenNew(AcronymValidator.class).withArguments(surveyDao, surveyForm).thenReturn(acronymValidator);
		when(acronymValidator.validate()).thenReturn(validatorResponse);
		whenNew(CustomIdValidator.class).withArguments(surveyDao, surveyForm).thenReturn(customIdValidator);
		when(customIdValidator.validate()).thenReturn(validatorResponse);
	}

	@Test
	public void validateSurvey_call_validators_but_not_throws_exceptions() throws Exception {
		when(validatorResponse.isValid()).thenReturn(POSITIVE_ANSWER);
		surveyValidatorService.validateSurvey(surveyDao, surveyForm);
		Mockito.verify(acronymValidator).validate();
		Mockito.verify(customIdValidator).validate();
	}

	@Test(expected = AlreadyExistException.class)
	public void acronymValidator_should_throw_AlreadyExistException_case_Acronym_already_exist() throws Exception {
		when(validatorResponse.isValid()).thenReturn(NEGATIVE_ANSWER);
		surveyValidatorService.validateSurvey(surveyDao, surveyForm);
	}

	@Test(expected = AlreadyExistException.class)
	public void customIdValidator_should_throw_AlreadyExistException_case_Item_ID_already_exist() throws Exception {
		when(validatorResponse.isValid()).thenReturn(NEGATIVE_ANSWER);
		surveyValidatorService.validateSurvey(surveyDao, surveyForm);
	}

}
