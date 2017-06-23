package br.org.otus.survey.validators;


import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;

import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.identity.Identity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.survey.SurveyDao;
import br.org.otus.survey.validators.AcronymValidator.Response;

@RunWith(PowerMockRunner.class)
public class AcronymValidatorTest {
	
	private static String ACRONYM = "TCLEC";
	
	@Mock
	private SurveyDao surveyDao;
	@Mock
	private SurveyForm surveyForm;	
	
	private AcronymValidator acronymValidator;
	private SurveyTemplate surveyTemplate;
		
	@Before
	public void setUp(){
		ArrayList<SurveyForm> founded = new ArrayList<>();
		//founded.add(surveyForm);
		
		
		surveyTemplate = new SurveyTemplate();
		
		Identity identity = new Identity();
		identity.acronym = ACRONYM;
		surveyTemplate.identity = identity;

		acronymValidator = new AcronymValidator(surveyDao, surveyForm);
		
		when(surveyForm.getSurveyTemplate())
		.thenReturn(surveyTemplate);
		
		when(surveyDao.findByAcronym(ACRONYM)).thenReturn(founded);		
	}
	
	
	
	@Test
	public void method_should_verify_acronymValidator_isEmpty(){
		assertTrue(acronymValidator.validate().isValid());
				
	}
	@Test
	public void method_verify_Response_instance_class(){
		assertTrue(Response.class.isInstance(acronymValidator.validate()));
				
	}
}


