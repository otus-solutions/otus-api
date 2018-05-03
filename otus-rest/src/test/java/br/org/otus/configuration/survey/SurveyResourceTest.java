package br.org.otus.configuration.survey;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.survey.api.SurveyFacade;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;

@RunWith(MockitoJUnitRunner.class)
public class SurveyResourceTest {
	private static final String USER_EMAIL = "otus@tus.com";
	private static final String ACRONYM = "USGC";
	private static final Boolean POSITIVE_RETURN = true;
	@InjectMocks
	private SurveyResource surveyResource;
	@Mock
	private SurveyFacade surveyFacade;
	@Mock
	private UpdateSurveyFormTypeDto updateSurveyFormTypeDto;
	private List<SurveyForm> surveys;
	private SurveyTemplate surveyTemplate;
	private SurveyForm surveyForm;

	@Before
	public void setUp() {
		surveys = new ArrayList<SurveyForm>();
		surveyTemplate = new SurveyTemplate();
		surveyForm = new SurveyForm(surveyTemplate, USER_EMAIL);
		surveys.add(surveyForm);
	}

	@Test
	public void method_getAll_should_return_response_in_surveyJson() {
		when(surveyFacade.list()).thenReturn(surveys);
		assertTrue(surveyResource.getAll().contains(USER_EMAIL));
	}

	@Test
	public void method_getByAcronym_should_return_response_in_surveyJson() {
		when(surveyFacade.findByAcronym(ACRONYM)).thenReturn(surveys);
		assertTrue(surveyResource.getByAcronym(ACRONYM).contains(USER_EMAIL));
	}

	@Test
	public void method_DeleteByAcronym_should_return_positive_answer_in_responseJson() {
		when(surveyFacade.deleteLastVersionByAcronym(ACRONYM)).thenReturn(POSITIVE_RETURN);
		assertTrue(surveyResource.deleteByAcronym(ACRONYM).contains(POSITIVE_RETURN.toString()));
	}

	@Test
	public void method_UpdateSurveyFormType_should_return_positive_answer_in_responseJson() {
		when(surveyFacade.updateSurveyFormType(updateSurveyFormTypeDto)).thenReturn(POSITIVE_RETURN);
		assertTrue(surveyResource.updateSurveyFormType(ACRONYM, updateSurveyFormTypeDto)
				.contains(POSITIVE_RETURN.toString()));
	}

}
