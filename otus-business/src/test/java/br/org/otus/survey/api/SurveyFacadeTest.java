package br.org.otus.survey.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.dtos.UpdateSurveyFormTypeDto;
import br.org.otus.survey.services.SurveyService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SurveyFacade.class)
public class SurveyFacadeTest {
	private static final String USER_EMAIL = "otus@tus.com";
	private static final String ACRONYM = "USGC";
	private static final String ACRONYM_ALREADY_EXIST = "Acronym";
	private static final Boolean POSITIVE_ANSWER = true;
	private static final Boolean NEGATIVE_ANSWER = false;
	private static final Integer VERSION = 1;
	@InjectMocks
	private SurveyFacade surveyFacade;
	@Mock
	private SurveyService surveyService;
	@Mock
	private SurveyForm survey;
	@Mock
	private UpdateSurveyFormTypeDto updateSurveyFormTypeDto;
	private List<SurveyForm> surveys;
	private SurveyTemplate surveyTemplate;
	private SurveyForm surveyAcronym;
	private Throwable e;

	@Before
	public void setUp() {
		surveys = new ArrayList<SurveyForm>();
		surveyTemplate = new SurveyTemplate();
		surveyAcronym = new SurveyForm(surveyTemplate, USER_EMAIL);
		e = spy(new AlreadyExistException());
	}

	@Test
	public void method_list_should_return_surveys() {
		surveys.add(survey);
		surveys.add(surveyAcronym);
		when(surveyService.listUndiscarded()).thenReturn(surveys);
		assertEquals(surveys.size(), surveyFacade.listUndiscarded().size());
	}

	@Test
	public void method_findByAcronym_should_return_survey_by_acronym() {
		surveys.add(surveyAcronym);
		when(surveyService.findByAcronym(ACRONYM)).thenReturn(surveys);
		assertEquals(surveys.size(), surveyFacade.findByAcronym(ACRONYM).size());
	}
	
	@Test
	public void method_findByAcronymWithVersion_should_return_survey_by_acronym() throws DataNotFoundException {
		surveys.add(surveyAcronym);
		when(surveyService.get(ACRONYM, VERSION)).thenReturn(surveys.get(0));
		assertEquals(surveys.get(0), surveyFacade.get(ACRONYM, VERSION));
	}

	@Test
	public void method_publishSurveyTemplate_should_return_surveyForm() throws Exception {
		whenNew(SurveyForm.class).withArguments(surveyTemplate, USER_EMAIL).thenReturn(survey);
		when(surveyService.saveSurvey(survey)).thenReturn(survey);
		assertTrue(surveyFacade.publishSurveyTemplate(surveyTemplate, USER_EMAIL) instanceof SurveyForm);
	}

	@Test(expected = HttpResponseException.class)
	public void method_publishSurveyTemplate_should_throws_HttpResponseException_with_NonUniqueItemID()
			throws Exception {
		whenNew(SurveyForm.class).withArguments(surveyTemplate, USER_EMAIL).thenReturn(survey);
		when(surveyService.saveSurvey(survey)).thenThrow(e);
		when(e.getCause()).thenReturn(e);
		when(e.getMessage()).thenReturn(ACRONYM);

		surveyFacade.publishSurveyTemplate(surveyTemplate, USER_EMAIL);
	}

	@Test
	public void method_updateSurveyFormType_should_return_valid() throws ValidationException, DataNotFoundException {
		when(surveyService.updateLastVersionSurveyType(updateSurveyFormTypeDto)).thenReturn(POSITIVE_ANSWER);
		assertTrue(surveyFacade.updateLastVersionSurveyType(updateSurveyFormTypeDto));
	}

	@Test
	public void method_updateSurveyFormType_should_return_invalid() throws ValidationException, DataNotFoundException {
		when(surveyService.updateLastVersionSurveyType(updateSurveyFormTypeDto)).thenReturn(NEGATIVE_ANSWER);
		assertFalse(surveyFacade.updateLastVersionSurveyType(updateSurveyFormTypeDto));
	}

	@Test(expected = HttpResponseException.class)
	public void method_updateSurveyFormType_should_throw_HttpResponseException() throws ValidationException, DataNotFoundException {
		when(surveyService.updateLastVersionSurveyType(updateSurveyFormTypeDto)).thenThrow(new ValidationException(new Throwable("")));
		assertFalse(surveyFacade.updateLastVersionSurveyType(updateSurveyFormTypeDto));
	}

	@Test
	public void method_deleteByAcronym_should_return_valid() throws ValidationException, DataNotFoundException {
		when(surveyService.deleteLastVersionByAcronym(ACRONYM)).thenReturn(POSITIVE_ANSWER);
		assertTrue(surveyFacade.deleteLastVersionByAcronym(ACRONYM));
	}

	@Test
	public void method_deleteByAcronym_should_return_invalid() throws ValidationException, DataNotFoundException {
		when(surveyService.deleteLastVersionByAcronym(ACRONYM)).thenReturn(NEGATIVE_ANSWER);
		assertFalse(surveyFacade.deleteLastVersionByAcronym(ACRONYM));
	}

	@Test(expected = HttpResponseException.class)
	public void method_deleteByAcronym_should_throw_HttpResponseException() throws ValidationException, DataNotFoundException {
		when(surveyService.deleteLastVersionByAcronym(ACRONYM)).thenThrow(new ValidationException(new Throwable("")));
		assertFalse(surveyFacade.deleteLastVersionByAcronym(ACRONYM));
	}

}