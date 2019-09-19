package br.org.otus.survey.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.identity.Identity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.survey.SurveyDaoBean;

@RunWith(MockitoJUnitRunner.class)
public class CustomIdValidatorTest {
	private static final String ACRONYM = "DIEC";

	@InjectMocks
	private CustomIdValidator customIdValidator;
	@Mock
	private SurveyDaoBean surveyDaoBean;
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


		surveyTemplate.identity = new Identity();
		surveyTemplate.identity.acronym = ACRONYM;
		when(surveyForm.getSurveyTemplate()).thenReturn(surveyTemplate);
		when(surveyForm.getSurveyTemplate().getCustomIds()).thenReturn(customIds);
	}

	@Test
	public void method_validate_should_return_validatorResponse_invalid() {
		surveyFormList.add(surveyFormReal);
		when(surveyDaoBean.findByCustomId(surveyForm.getSurveyTemplate().getCustomIds(), ACRONYM))
				.thenReturn(surveyFormList);
		assertFalse(customIdValidator.validate().isValid());
	}

	@Test
	public void method_validate_should_return_validatorResponse_valid() {
		when(surveyDaoBean.findByCustomId(surveyForm.getSurveyTemplate().getCustomIds(), ACRONYM))
				.thenReturn(surveyFormList);
		assertTrue(customIdValidator.validate().isValid());
	}
}
