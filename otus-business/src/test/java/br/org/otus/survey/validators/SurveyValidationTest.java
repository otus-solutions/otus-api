package br.org.otus.survey.validators;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SurveyValidationTest {
	@InjectMocks
	@Spy
	private SurveyValidation surveyValidation = new SurveyValidation();
	@Spy
	private Set<ValidatorResponse> responses = new HashSet<>();
	@Mock
	private ValidatorResponse validatorResponse;

	@Test
	public void method_isValid_should_return_valid_in_case_responseSet_exist() {
		assertTrue(surveyValidation.isValid());
	}

	@Test
	public void method_AddValidatorResponse_should_evocate_method_add_of_responsesSet() {
		surveyValidation.addValidatorResponse(validatorResponse);
		Mockito.verify(responses).add(validatorResponse);
	}
}
