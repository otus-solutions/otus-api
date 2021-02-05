package br.org.otus.survey.services;

import br.org.otus.LoggerTestsParent;
import br.org.otus.extraction.ActivityExtractionFacade;
import br.org.otus.model.User;
import br.org.otus.outcomes.FollowUpFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.user.management.ManagementUserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.bson.types.ObjectId;
import org.ccem.otus.enums.AuthenticationMode;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;
import org.ccem.otus.service.sharing.ActivitySharingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SignedJWT.class})
public class ActivityTasksServiceBeanTest extends LoggerTestsParent {

  private static final Long RECRUITMENT_NUMBER = 5112345L;
  private static final String ACTIVITY_ID   = "5c0e5d41e69a69006430cb75";
  private static final String ACTIVITY_ID_2 = "5c0e5d41e69a69006430cb76";
  private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJvdHVzQGdtYWlsLmNvbSIsIm1vZGUiOiJ1c2VyIiwianRpIjoiYzc1ODIzNWMtYjQzMy00NDQ2LWFhMDMtYmU0NmI3ODU3NWEyIiwiaWF0IjoxNTg1MTc2NDg5LCJleHAiOjE1ODUxODAwODl9.wlSmhUXYW6Apqg5skGPLDGCyuA0sDYyVtZIBM8RxkLs";
  private static final String TOKEN_BEARER = "Bearer " + TOKEN;
  private static final String PARTICIPANT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6InJvemFsaW5vQGdtYWlsLmNvbSJ9.OStBf7OdE38uTpiQ25XpWxiC3ujsYK6OvZ4rSzQrB-Y";
  private static final String PARTICIPANT_TOKEN_BEARER = "Bearer " + PARTICIPANT_TOKEN;
  private static final String USER_EMAIL = "otus@gmail.com";
  private static final boolean NOTIFY = false;
  private static final String ACTIVITY_SHARING_ID = "5c0e5d41e69a69006430cb74";
  private static final ObjectId ACTIVITY_SHARING_OID = new ObjectId(ACTIVITY_SHARING_ID);
  private static final String ACTIVITY_STATUS_USER = "{" +
    "\"name\" : \"Fulano\"," +
    "\"surname\" : \"De Tal\"," +
    "\"phone\" : \"5199999999\"," +
    "\"email\" : \"user@otus.com\"" +
    "}";
  private static final String SURVEY_ACTIVITY_JSON = "{" +
    "\"objectType\" : \"Activity\"," +
    "\"statusHistory\": [" +
      "{" +
        "\"objectType\" : \"ActivityStatus\"," +
        "\"name\" : \"CREATED\"," +
        "\"date\" : \"2020-12-18T16:55:55.511Z\"," +
        "\"user\" : " + ACTIVITY_STATUS_USER +
      "}," +
      "{" +
        "\"objectType\" : \"ActivityStatus\"," +
        "\"name\" : \"INITIALIZED_ONLINE\"," +
        "\"date\" : \"2020-12-18T16:55:55.511Z\"," +
        "\"user\" : " + ACTIVITY_STATUS_USER +
      "}," +
      "{" +
        "\"objectType\" : \"ActivityStatus\"," +
        "\"name\" : \"SAVED\"," +
        "\"date\" : \"2020-12-18T17:55:55.511Z\"" +
      "}" +
    "]" +
    "}";

  @InjectMocks
  private ActivityTasksServiceBean service;

  @Mock
  private ActivityService activityService;
  @Mock
  private ParticipantService participantService;
  @Mock
  private ManagementUserService managementUserService;
  @Mock
  private FollowUpFacade followUpFacade;
  @Mock
  private ActivitySharingService activitySharingService;
  @Mock
  private ActivityExtractionFacade extractionFacade;

  private SurveyActivity surveyActivity;
  private SurveyActivity surveyActivityToUpdate;
  private OfflineActivityCollection offlineActivityCollection;

  @Before
  public void setUp() throws Exception {
    setUpLogger(ActivityTasksServiceBean.class);

    surveyActivity = PowerMockito.spy(SurveyActivity.deserialize(SURVEY_ACTIVITY_JSON));
    surveyActivityToUpdate = PowerMockito.spy(SurveyActivity.deserialize(SURVEY_ACTIVITY_JSON));

    when(activityService.create(surveyActivity)).thenReturn(ACTIVITY_ID);
  }


  @Test
  public void create_method_should_create_surveyActivity_and_extraction() throws InterruptedException {
    when(surveyActivity.getMode()).thenReturn(ActivityMode.ONLINE);

    String result = service.create(surveyActivity, NOTIFY);
    Thread.sleep(100);

    assertNotNull(surveyActivity.getActivityID());
    assertEquals(ACTIVITY_ID, surveyActivity.getActivityID().toString());
    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
    assertEquals(ACTIVITY_ID, result);
  }

  @Test
  public void create_method_should_create_surveyActivity_and_extraction_and_autoFill_event() throws InterruptedException {
    PowerMockito.when(surveyActivity.getMode()).thenReturn(ActivityMode.AUTOFILL);

    String result = service.create(surveyActivity, NOTIFY);
    Thread.sleep(100);

    assertEquals(ACTIVITY_ID, result);
    assertNotNull(surveyActivity.getActivityID());
    assertEquals(ACTIVITY_ID, surveyActivity.getActivityID().toString());
    verify(followUpFacade, times(1)).createParticipantActivityAutoFillEvent(surveyActivity, NOTIFY);
    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
  }

  @Test
  public void create_method_should_log_extraction_creation_exception() throws InterruptedException {
    doThrow(new HttpResponseException(null)).when(extractionFacade).createOrUpdateActivityExtraction(ACTIVITY_ID);
    when(surveyActivity.getMode()).thenReturn(ActivityMode.ONLINE);

    String result = service.create(surveyActivity, NOTIFY);
    Thread.sleep(100);

    assertEquals(ACTIVITY_ID, result);
    assertNotNull(surveyActivity.getActivityID());
    assertEquals(ACTIVITY_ID, surveyActivity.getActivityID().toString());
    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
    verifyLoggerSevereWasCalled();
  }


  @Test
  public void updateActivity_method_should_update_surveyActivity_and_extraction() throws Exception {
    mockUserForUpdate();
    setUpdatedActivity(ActivityMode.ONLINE);

    SurveyActivity updatedActivity = service.updateActivity(surveyActivityToUpdate, TOKEN_BEARER);
    Thread.sleep(100);

    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
    assertEquals(surveyActivityToUpdate, updatedActivity);
  }

  @Test
  public void updateActivity_method_should_update_autofillSurveyActivity_and_extraction_and_autoFill_event() throws Exception {
    mockParticipantUserForUpdate();
    setUpdatedActivity(ActivityMode.AUTOFILL);

    SurveyActivity updatedActivity = service.updateActivity(surveyActivityToUpdate, PARTICIPANT_TOKEN_BEARER);
    Thread.sleep(100);

    assertEquals(surveyActivityToUpdate, updatedActivity);

    String lastStatusHistoryName = surveyActivityToUpdate.getLastStatus().get().getName();
    verify(followUpFacade, times(1)).statusUpdateEvent(lastStatusHistoryName, ACTIVITY_ID);

    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
  }

  @Test
  public void updateActivity_method_should_update_autofillSurveyActivity_and_cancel_event_and_delete_shared_URL() throws Exception {
    mockParticipantUserForUpdateActivitySharing();
    setUpdatedActivity(ActivityMode.AUTOFILL);
    when(surveyActivityToUpdate.isDiscarded()).thenReturn(true);
    when(activitySharingService.getActivitySharingIdByActivityId(surveyActivityToUpdate.getActivityID()))
      .thenReturn(ACTIVITY_SHARING_OID);

    SurveyActivity updatedActivity = service.updateActivity(surveyActivityToUpdate, PARTICIPANT_TOKEN_BEARER);
    Thread.sleep(100);

    assertEquals(surveyActivityToUpdate, updatedActivity);
    verify(followUpFacade, times(1)).cancelParticipantEventByActivityId(ACTIVITY_ID);
    verify(activitySharingService, times(1)).deleteSharedURL(ACTIVITY_SHARING_ID);
    verify(extractionFacade, times(1)).deleteActivityExtraction(ACTIVITY_ID);
  }

  @Test
  public void updateActivity_method_should_log_extraction_update_exception() throws Exception {
    mockUserForUpdate();
    setUpdatedActivity(ActivityMode.AUTOFILL);
    doThrow(new HttpResponseException(null)).when(extractionFacade).createOrUpdateActivityExtraction(ACTIVITY_ID);

    SurveyActivity updatedActivity = service.updateActivity(surveyActivityToUpdate, TOKEN_BEARER);
    Thread.sleep(100);

    assertEquals(surveyActivityToUpdate, updatedActivity);
    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
    verifyLoggerSevereWasCalled();
  }

  @Test(expected = HttpResponseException.class)
  public void updateActivity_method_should_throw_HttpResponseException_in_case_invalid_token_mode() throws Exception {
    mockInvalidToken(TOKEN);
    service.updateActivity(surveyActivityToUpdate, TOKEN_BEARER);
  }


  @Test
  public void save_method_should_save_offlineActivityCollection_surveyActivities_and_each_extraction() throws InterruptedException, DataNotFoundException {
    setOfflineActivityCollection();

    service.save(USER_EMAIL, offlineActivityCollection);
    Thread.sleep(100);

    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID_2);
  }

  @Test
  public void save_method_should_log_extraction_update_exception() throws InterruptedException, DataNotFoundException {
    setOfflineActivityCollection();
    doThrow(new HttpResponseException(null)).when(extractionFacade).createOrUpdateActivityExtraction(ACTIVITY_ID);

    service.save(USER_EMAIL, offlineActivityCollection);
    Thread.sleep(100);

    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
    verify(extractionFacade, times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID_2);
    verifyLoggerSevereWasCalled();
  }


  @Test
  public void discardById_method_should_call_discardById_from_activityService_and_delete_activityExtraction() throws InterruptedException, DataNotFoundException {
    service.discardById(ACTIVITY_ID);
    Thread.sleep(100);
    verify(extractionFacade, times(1)).deleteActivityExtraction(ACTIVITY_ID);
  }

  @Test
  public void discardById_method_should_log_extraction_update_exception() throws InterruptedException, DataNotFoundException {
    doThrow(new HttpResponseException(null)).when(extractionFacade).deleteActivityExtraction(ACTIVITY_ID);
    service.discardById(ACTIVITY_ID);
    Thread.sleep(100);
    verify(extractionFacade, times(1)).deleteActivityExtraction(ACTIVITY_ID);
    verifyLoggerSevereWasCalled();
  }


  private void setUpdatedActivity(ActivityMode activityMode) throws Exception {
    surveyActivityToUpdate.setActivityID(new ObjectId(ACTIVITY_ID));
    when(surveyActivityToUpdate.getMode()).thenReturn(activityMode);
    when(activityService, "update", surveyActivityToUpdate).thenReturn(surveyActivityToUpdate);
  }

  private void mockUserForUpdate() throws Exception {
    br.org.otus.model.User user = new User();
    user.setEmail(USER_EMAIL);
    when(managementUserService.fetchByEmail(USER_EMAIL)).thenReturn(user);

    SignedJWT signedJWT = PowerMockito.spy(SignedJWT.parse(TOKEN));
    mockStatic(SignedJWT.class);
    when(SignedJWT.class, "parse", TOKEN).thenReturn(signedJWT);
  }

  private void mockParticipantUserForUpdate() throws Exception {
    Participant participant = new Participant(RECRUITMENT_NUMBER);
    participant.setEmail(USER_EMAIL);
    when(participantService.getByEmail(USER_EMAIL)).thenReturn(participant);

    SignedJWT signedJWT = PowerMockito.spy(SignedJWT.parse(PARTICIPANT_TOKEN));
    mockStatic(SignedJWT.class);
    when(SignedJWT.class, "parse", PARTICIPANT_TOKEN).thenReturn(signedJWT);

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
      .claim("mode", AuthenticationMode.PARTICIPANT.getName())
      .claim("iss", USER_EMAIL)
      .build();
    when(signedJWT.getJWTClaimsSet()).thenReturn(claimsSet);
  }

  private void mockParticipantUserForUpdateActivitySharing() throws Exception {
    Participant participant = new Participant(RECRUITMENT_NUMBER);
    participant.setEmail(USER_EMAIL);
    when(participantService.getByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);

    SignedJWT signedJWT = PowerMockito.spy(SignedJWT.parse(PARTICIPANT_TOKEN));
    mockStatic(SignedJWT.class);
    when(SignedJWT.class, "parse", PARTICIPANT_TOKEN).thenReturn(signedJWT);

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
      .claim("mode", AuthenticationMode.ACTIVITY_SHARING.getName())
      .claim("iss", USER_EMAIL)
      .claim("recruitmentNumber", RECRUITMENT_NUMBER)
      .build();
    when(signedJWT.getJWTClaimsSet()).thenReturn(claimsSet);
  }

  private void mockInvalidToken(String token) throws Exception {
    SignedJWT signedJWT = PowerMockito.spy(SignedJWT.parse(token));
    mockStatic(SignedJWT.class);
    when(SignedJWT.class, "parse", token).thenReturn(signedJWT);

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
      .claim("mode", AuthenticationMode.CLIENT.getName())
      .claim("iss", USER_EMAIL)
      .build();
    when(signedJWT.getJWTClaimsSet()).thenReturn(claimsSet);
  }

  private void setOfflineActivityCollection(){
    surveyActivity.setActivityID(new ObjectId(ACTIVITY_ID));

    SurveyActivity surveyActivity2 = PowerMockito.spy(SurveyActivity.deserialize(SURVEY_ACTIVITY_JSON));
    surveyActivity2.setActivityID(new ObjectId(ACTIVITY_ID_2));

    List<SurveyActivity> activities = new ArrayList<>();
    activities.add(surveyActivity);
    activities.add(surveyActivity2);

    offlineActivityCollection = PowerMockito.spy(new OfflineActivityCollection());
    Whitebox.setInternalState(offlineActivityCollection, "activities", activities);
  }

}
