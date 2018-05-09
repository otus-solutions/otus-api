package br.org.otus.survey.activity.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.survey.form.SurveyForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;

import br.org.otus.response.exception.HttpResponseException;

@RunWith(MockitoJUnitRunner.class)
public class ActivityFacadeTest {
	private static final long RECRUITMENT_NUMBER = 5112345;
	private static final String SURVEYACTIVITY_ID = "587723451798";
	private static final String SURVEYACTIVITY_EXCEPTION = "notExist";
	private static final String JSON = "" + "{\"objectType\" : \"Activity\"," + "\"extents\" : \"StudioObject\"}";
	private static final Integer VERSION = 1;
	@Mock
	private SurveyActivity surveyActivityInvalid;
	@Mock
	private ActivityService activityService;
	@InjectMocks
	ActivityFacade activityFacade;
	@Mock
	private SurveyActivity surveyActivity;
	private SurveyActivity surveyActivityFull;
	

	@Before
	public void setUp() {
		surveyActivityFull = new Gson().fromJson(JSON, SurveyActivity.class);
	}

	@Test
	public void method_should_verify_list_with_rn() {
		when(activityService.list(RECRUITMENT_NUMBER)).thenReturn(new ArrayList<>());
		activityFacade.list(RECRUITMENT_NUMBER);
		verify(activityService).list(RECRUITMENT_NUMBER);
	}

	@Test
	public void method_should_verify_getByID_with_id() throws DataNotFoundException {
		when(activityService.getByID(SURVEYACTIVITY_ID)).thenReturn(surveyActivity);
		activityFacade.getByID(SURVEYACTIVITY_ID);
		verify(activityService).getByID(SURVEYACTIVITY_ID);
	}
	
	@Test
	public void method_should_verify_getAllByIDWithVersion_with_id_and_version() throws DataNotFoundException {
		List<SurveyActivity> list = new ArrayList<SurveyActivity>();
		list.add(surveyActivity);
		list.add(surveyActivity);
		when(activityService.getAllByIDWithVersion(SURVEYACTIVITY_ID, VERSION)).thenReturn(list);
		activityFacade.getAllByIDWithVersion(SURVEYACTIVITY_ID, VERSION);
		verify(activityService).getAllByIDWithVersion(SURVEYACTIVITY_ID, VERSION);
	}

	@Test(expected = HttpResponseException.class)
	public void method_should_throw_HttpResponseException_getById_invalid() throws Exception {
		when(activityService.getByID(SURVEYACTIVITY_EXCEPTION)).thenThrow(new HttpResponseException(null));
		activityFacade.getByID(SURVEYACTIVITY_EXCEPTION);
	}

	@Test
	public void method_should_verify_create_with_surveyActivity() {
		when(activityService.create(surveyActivity)).thenReturn(SURVEYACTIVITY_ID);
		activityFacade.create(surveyActivity);
		verify(activityService).create(surveyActivity);
	}

	@Test
	public void method_should_verify_updateActivity_with_surveyActivity() throws DataNotFoundException {
		when(activityService.update(surveyActivity)).thenReturn(surveyActivity);
		activityFacade.updateActivity(surveyActivity);
		verify(activityService).update(surveyActivity);
	}

	@Test(expected = HttpResponseException.class)
	public void method_should_throw_HttpResponseException_updateActivity_invalid() throws Exception {
		when(activityService.update(surveyActivity)).thenThrow(new HttpResponseException(null));
		activityFacade.updateActivity(surveyActivity);
	}
}
