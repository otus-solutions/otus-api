package br.org.otus.survey.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.survey.SurveyDao;

@RunWith(MockitoJUnitRunner.class)
public class CustomIdValidatorTest {
	@InjectMocks
	private CustomIdValidator customIdValidator;
	@Mock
	private SurveyDao surveyDao;
	@Mock
	private SurveyForm surveyForm;
	@Mock
	private SurveyTemplate surveyTemplate;
	private String surveyJsonDeserialize;
	private SurveyForm surveyFormReal;
	private Set<String> customIds;
	private List<SurveyForm> surveyFormList;

	@Before
	public void setUp() {
		surveyJsonDeserialize = SurveyFormFactory.create().toString();
		surveyFormReal = SurveyForm.deserialize(surveyJsonDeserialize);
		customIds = surveyFormReal.getSurveyTemplate().getCustomIds();
		surveyFormList = new ArrayList<>();
		Mockito.when(surveyForm.getSurveyTemplate()).thenReturn(surveyTemplate);
		Mockito.when(surveyForm.getSurveyTemplate().getCustomIds()).thenReturn(customIds);
	}

	@Test
	public void method_validate_should_return_validatorResponse_invalid() {
		surveyFormList.add(surveyFormReal);
		Mockito.when(surveyDao.findByCustomId(surveyForm.getSurveyTemplate().getCustomIds()))
				.thenReturn(surveyFormList);
		assertFalse(customIdValidator.validate().isValid());
	}

	@Test
	public void method_validate_should_return_validatorResponse_valid() {
		Mockito.when(surveyDao.findByCustomId(surveyForm.getSurveyTemplate().getCustomIds()))
				.thenReturn(surveyFormList);
		assertTrue(customIdValidator.validate().isValid());
	}
}
