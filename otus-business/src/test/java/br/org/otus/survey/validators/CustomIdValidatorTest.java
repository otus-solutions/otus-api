package br.org.otus.survey.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.powermock.api.mockito.PowerMockito.when;
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
import org.powermock.modules.junit4.PowerMockRunner;
import br.org.otus.survey.SurveyDao;

@RunWith(PowerMockRunner.class)
public class CustomIdValidatorTest {

	@InjectMocks
	CustomIdValidator customIdValidator;
	@Mock
	SurveyDao surveyDao;
	@Mock
	SurveyForm surveyForm;
	@Mock
	SurveyTemplate surveyTemplate;

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
		when(surveyForm.getSurveyTemplate()).thenReturn(surveyTemplate);
		when(surveyForm.getSurveyTemplate().getCustomIds()).thenReturn(customIds);
	}

	@Test
	public void method_validate_should_return_validatorResponse_invalid() {
		surveyFormList.add(surveyFormReal);
		when(surveyDao.findByCustomId(surveyForm.getSurveyTemplate().getCustomIds())).thenReturn(surveyFormList);
		assertFalse(customIdValidator.validate().isValid());
	}

	@Test
	public void method_validate_should_return_validatorResponse_valid() {
		when(surveyDao.findByCustomId(surveyForm.getSurveyTemplate().getCustomIds())).thenReturn(surveyFormList);
		assertTrue(customIdValidator.validate().isValid());
	}

}
