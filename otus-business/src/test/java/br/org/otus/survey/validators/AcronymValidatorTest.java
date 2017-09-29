package br.org.otus.survey.validators;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.identity.Identity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.survey.SurveyDao;
import br.org.otus.survey.validators.AcronymValidator.Response;

@RunWith(MockitoJUnitRunner.class)
public class AcronymValidatorTest {
	private static String ACRONYM = "TCLEC";
	@InjectMocks
	private AcronymValidator acronymValidator;
	@Mock
	private SurveyDao surveyDao;
	@Mock
	private SurveyForm surveyForm;
	@Mock
	private SurveyTemplate surveyTemplate;

	@Before
	public void setUp() throws Exception {
		ArrayList<SurveyForm> founded = new ArrayList<>();
		Identity identity = new Identity();
		identity.acronym = ACRONYM;
		surveyTemplate.identity = identity;
		Mockito.when(surveyForm.getSurveyTemplate()).thenReturn(surveyTemplate);
		Mockito.when(surveyDao.findByAcronym(ACRONYM)).thenReturn(founded);
	}

	@Test
	public void method_verify_return_Response_instance_class() {
		assertTrue(Response.class.isInstance(acronymValidator.validate()));
	}

	@Test
	public void method_verify_acronymValidator_isEmpty() {
		assertTrue(acronymValidator.validate().isValid());
	}

}
