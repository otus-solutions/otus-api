package br.org.otus.survey.activity.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.ActivityService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.services.SurveyService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivityFacade.class})
public class ActivityFacadeTest {
  private static final long RECRUITMENT_NUMBER = 5112345;
  private static final String ACRONYM = "CISE";
  private static final String TOKEN = "TOKEN";
  private static final String SURVEY_ACTIVITY_EXCEPTION = "notExist";
  private static final String JSON = "" + "{\"objectType\" : \"Activity\"," + "\"extents\" : \"StudioObject\"}";
  private static final Integer VERSION = 1;
  private static final String USER_EMAIL = "otus@gmail.com";
  private static final String checkerUpdated = "{\"id\":\"5c0e5d41e69a69006430cb75\",\"activityStatus\":{\"objectType\":\"ActivityStatus\",\"name\":\"INITIALIZED_OFFLINE\",\"date\":\"2018-12-10T12:33:29.007Z\",\"user\":{\"name\":\"Otus\",\"surname\":\"Solutions\",\"extraction\":true,\"extractionIps\":[\"999.99.999.99\"],\"phone\":\"21987654321\",\"fieldCenter\":{},\"email\":\"otus@gmail.com\",\"admin\":false,\"enable\":true,\"meta\":{\"revision\":0,\"created\":0,\"version\":0},\"$loki\":2}}}";
  private static final String CENTER = "RS";

  @Mock
  private SurveyActivity surveyActivityInvalid;
  @Mock
  private ActivityService activityService;
  @InjectMocks
  ActivityFacade activityFacade;
  @Mock
  private SurveyActivity surveyActivity;
  @Mock
  private SurveyService surveyService;
  private SurveyActivity surveyActivityFull;
//TODO test for use gatewayfacade private GatewayFacade gatewayFacade;

  @Before
  public void setUp() {
    surveyActivityFull = new Gson().fromJson(JSON, SurveyActivity.class);
  }

  @Test
  public void method_should_verify_list_with_rn() {
    when(activityService.list(RECRUITMENT_NUMBER, USER_EMAIL)).thenReturn(new ArrayList<>());
    activityFacade.list(RECRUITMENT_NUMBER, USER_EMAIL);
    verify(activityService, times(1)).list(RECRUITMENT_NUMBER, USER_EMAIL);
  }

  @Test
  public void method_should_verify_get_with_id() throws DataNotFoundException {
    when(activityService.getByID(ACRONYM)).thenReturn(surveyActivity);
    activityFacade.getByID(ACRONYM);
    verify(activityService, times(1)).getByID(ACRONYM);
  }

  @Test
  public void method_should_verify_get_with_id_and_version()
    throws DataNotFoundException, InterruptedException, MemoryExcededException {
    List<SurveyActivity> list = new ArrayList<SurveyActivity>();
    list.add(surveyActivity);
    list.add(surveyActivity);
    when(activityService.get(ACRONYM, VERSION)).thenReturn(list);
    activityFacade.get(ACRONYM, VERSION);
    verify(activityService, times(1)).get(ACRONYM, VERSION);
  }

  @Test(expected = HttpResponseException.class)
  public void method_should_throw_HttpResponseException_getById_invalid() throws Exception {
    when(activityService.getByID(SURVEY_ACTIVITY_EXCEPTION)).thenThrow(new HttpResponseException(null));
    activityFacade.getByID(SURVEY_ACTIVITY_EXCEPTION);
  }

  @Test
  public void method_should_verify_create_with_surveyActivity() {
    when(activityService.create(surveyActivity)).thenReturn(ACRONYM);
    activityFacade.create(surveyActivity);
    verify(activityService, times(1)).create(surveyActivity);
  }

  @Test(expected = HttpResponseException.class)
  public void createMethodTest_should_trigger_requiredExternalID_validation() {
    PowerMockito.when(surveyActivity.hasRequiredExternalID()).thenReturn(true);
    activityFacade.create(surveyActivity);
  }

  @Ignore
  @Test
  public void method_should_verify_updateActivity_with_surveyActivity() throws DataNotFoundException {
    when(activityService.update(surveyActivity)).thenReturn(surveyActivity);
    activityFacade.updateActivity(surveyActivity,TOKEN);
    verify(activityService, times(1)).update(surveyActivity);
  }

  @Ignore
  @Test(expected = HttpResponseException.class)
  public void method_should_throw_HttpResponseException_updateActivity_invalid() throws Exception {
    when(activityService.update(surveyActivity)).thenThrow(new HttpResponseException(null));
    activityFacade.updateActivity(surveyActivity, TOKEN);
  }

  @Test
  public void updateCheckerActivityMethod_should_invoke_updateCheckerActivity_of_ActivityService() throws DataNotFoundException {
    activityFacade.updateCheckerActivity(checkerUpdated);
    verify(activityService, times(1)).updateCheckerActivity(checkerUpdated);
  }

  @Test(expected = HttpResponseException.class)
  public void updateCheckerActivityMethod_should_throw_HttpResponseException_when_ObjectId_invalid() throws Exception {
    when(activityService.updateCheckerActivity(checkerUpdated)).thenThrow(new DataNotFoundException(new Throwable("Activity of Participant not found")));
    activityFacade.updateCheckerActivity(checkerUpdated);
  }

  @Test
  public void getActivityProgressExtraction_method_should_call_method_getActivityProgressExtraction_of_service() throws DataNotFoundException {
    activityFacade.getActivityProgressExtraction(CENTER);
    verify(activityService, times(1)).getActivityProgressExtraction(CENTER);
  }

  @Test
  public void getParticipantFieldCenterByActivity_method_should_call_method_getParticipantFieldCenterByActivity_of_service() throws DataNotFoundException {
    activityFacade.getParticipantFieldCenterByActivity(ACRONYM, VERSION);
    verify(activityService, times(1)).getParticipantFieldCenterByActivity(ACRONYM, VERSION);
  }

  @Test(expected = HttpResponseException.class)
  public void getParticipantFieldCenterByActivityMethod_should_throw_HttpResponseException_when_activity_invalid() throws Exception {
    when(activityService.getParticipantFieldCenterByActivity(ACRONYM, VERSION)).thenThrow(new DataNotFoundException(new Throwable("Activity of Participant not found")));
    activityFacade.getParticipantFieldCenterByActivity(ACRONYM, VERSION);
  }
}
