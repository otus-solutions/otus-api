package br.org.otus.survey.activity.sharing;

import br.org.otus.commons.FindByTokenService;
import br.org.otus.model.User;
import br.org.otus.response.exception.HttpResponseException;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.sharing.ActivitySharingService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivitySharingFacade.class})
public class ActivitySharingFacadeTest {

  private static final Long RN = 123456L;
  private static final String ACTIVITY_ID = "5a33cb4a28f10d1043710f7d";
  private static final String USER_ID = "5e0658135b4ff40f8916d2b5";
  private static final String USER_TOKEN = "123456";
  private static final String PARTICIPANT_TOKEN = "123456";
  private static final String SHARED_URL = "https://otus";

  @InjectMocks
  private ActivitySharingFacade activitySharingFacade = PowerMockito.spy(new ActivitySharingFacade());
  @Mock
  private ActivitySharingService activitySharingService;

  @Mock
  private ActivityService activityService;

  @Mock
  private ParticipantService participantService;

  @Mock
  private FindByTokenService findByTokenService;

  private Participant participant;
  private User user;
  private ActivitySharing activitySharing;
  private SurveyActivity surveyActivity;


  @Before
  public void setUp() throws Exception {
    ObjectId activityOID = new ObjectId(ACTIVITY_ID);
    ObjectId userOID = new ObjectId(USER_ID);

    participant = new Participant(RN);
    user = new User();
    user.set_id(userOID);

    activitySharing = new ActivitySharing(activityOID, userOID, PARTICIPANT_TOKEN);
    surveyActivity = new SurveyActivity();
    surveyActivity.setActivityID(activityOID);
    surveyActivity.setMode(ActivityMode.AUTOFILL);
    surveyActivity.setParticipantData(participant);
  }


  @Test
  public void getSharedURL_method_should_return_url() throws Exception {
    mock_buildActivitySharing_private_method_to_return_activitySharing();
    when(activitySharingService.getSharedURL(activitySharing)).thenReturn(SHARED_URL);
    assertEquals(
      SHARED_URL,
      activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN)
    );
  }

  @Test(expected = HttpResponseException.class)
  public void getSharedURL_method_should_throw_exception_in_case_not_fill_activity() throws Exception {
    surveyActivity.setMode(ActivityMode.ONLINE);
    mockInitialize();
    activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void  getSharedURL_method_should_handle_DataNotFoundException() throws Exception {
    mock_buildActivitySharing_private_method_to_return_activitySharing();
    doReturn(activitySharing).when(activitySharingFacade, "buildActivitySharing", ACTIVITY_ID, USER_TOKEN);
    when(activitySharingService.getSharedURL(activitySharing)).thenThrow(new DataNotFoundException());
    activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void  getSharedURL_method_should_handle_ValidationException() throws Exception {
    mock_buildActivitySharing_private_method_to_throw_exception(new ValidationException());
    activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void  getSharedURL_method_should_handle_ParseException() throws Exception {
    mock_buildActivitySharing_private_method_to_throw_exception(new ParseException("", 0));
    activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }


  @Test
  public void renovateSharedURL_method_should_return_url() throws Exception {
    mock_buildActivitySharing_private_method_to_return_activitySharing();
    when(activitySharingService.renovateSharedURL(activitySharing)).thenReturn(SHARED_URL);
    assertEquals(
      SHARED_URL,
      activitySharingFacade.renovateSharedURL(ACTIVITY_ID, USER_TOKEN)
    );
  }

  @Test(expected = HttpResponseException.class)
  public void renovateSharedURL_method_should_throw_exception_in_case_not_fill_activity() throws Exception {
    surveyActivity.setMode(ActivityMode.ONLINE);
    mockInitialize();
    activitySharingFacade.renovateSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void  renovateSharedURL_method_should_handle_DataNotFoundException() throws Exception {
    mock_buildActivitySharing_private_method_to_return_activitySharing();
    doReturn(activitySharing).when(activitySharingFacade, "buildActivitySharing", ACTIVITY_ID, USER_TOKEN);
    when(activitySharingService.renovateSharedURL(activitySharing)).thenThrow(new DataNotFoundException());
    activitySharingFacade.renovateSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void  renovateSharedURL_method_should_handle_ValidationException() throws Exception {
    mock_buildActivitySharing_private_method_to_throw_exception(new ValidationException());
    activitySharingFacade.renovateSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void  renovateSharedURL_method_should_handle_ParseException() throws Exception {
    mock_buildActivitySharing_private_method_to_throw_exception(new ParseException("", 0));
    activitySharingFacade.renovateSharedURL(ACTIVITY_ID, USER_TOKEN);
  }


  @Test
  public void deleteSharedURL_method_should_return_url() throws Exception {
    mockInitialize();
    activitySharingFacade.deleteSharedURL(ACTIVITY_ID);
    verify(activitySharingService, Mockito.times(1)).deleteSharedURL(ACTIVITY_ID);
  }

  @Test(expected = HttpResponseException.class)
  public void deleteSharedURL_method_should_throw_exception_in_case_not_fill_activity() throws Exception {
    surveyActivity.setMode(ActivityMode.ONLINE);
    mockInitialize();
    activitySharingFacade.deleteSharedURL(ACTIVITY_ID);
  }

  @Test(expected = HttpResponseException.class)
  public void deleteSharedURL_method_should_handle_DataNotFoundException() throws Exception {
    mockInitialize();
    doThrow(new DataNotFoundException()).when(activitySharingService, "deleteSharedURL", ACTIVITY_ID);
    activitySharingFacade.deleteSharedURL(ACTIVITY_ID);
  }


  private void mockInitialize() {
    try {
      when(activityService.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);
      when(findByTokenService.findUserByToken(USER_TOKEN)).thenReturn(user);
    }
    catch (Exception e){}
  }

  private void mock_buildActivitySharing_private_method_to_return_activitySharing() throws Exception {
    doReturn(activitySharing).when(activitySharingFacade, "buildActivitySharing", ACTIVITY_ID, USER_TOKEN);
  }

  private void mock_buildActivitySharing_private_method_to_throw_exception(Exception e) throws Exception {
    doThrow(e).when(activitySharingFacade, "buildActivitySharing", ACTIVITY_ID, USER_TOKEN);
  }
}
