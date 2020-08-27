package br.org.otus.survey.activity.sharing;

import br.org.otus.commons.FindByTokenService;
import br.org.otus.model.User;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.security.dtos.ParticipantTempTokenRequestDto;
import br.org.otus.security.services.TemporaryParticipantTokenService;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.SurveyActivity;

import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharingDto;
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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivitySharingFacade.class, ActivitySharing.class, ParticipantTempTokenRequestDto.class})
public class ActivitySharingFacadeTest {

  private static final Long RN = 123456L;
  private static final String ACTIVITY_ID = "5a33cb4a28f10d1043710f7d";
  private static final ObjectId ACTIVITY_OID = new ObjectId(ACTIVITY_ID);
  private static final String USER_ID = "5e0658135b4ff40f8916d2b5";
  private static final ObjectId USER_OID = new ObjectId(USER_ID);
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
  @Mock
  private TemporaryParticipantTokenService temporaryParticipantTokenService;

  private Participant participant;
  private User user;
  private ActivitySharing activitySharing;
  private SurveyActivity surveyActivity;
  private ParticipantTempTokenRequestDto participantTempTokenRequestDto;
  private ActivitySharingDto activitySharingDto;


  @Before
  public void setUp() throws Exception {
    participant = new Participant(RN);
    user = new User();
    user.set_id(USER_OID);

    activitySharing = new ActivitySharing(ACTIVITY_OID, USER_OID, PARTICIPANT_TOKEN);
    surveyActivity = new SurveyActivity();
    surveyActivity.setActivityID(ACTIVITY_OID);
    surveyActivity.setMode(ActivityMode.AUTOFILL);
    surveyActivity.setParticipantData(participant);

    participantTempTokenRequestDto = new ParticipantTempTokenRequestDto(RN, USER_ID);
    activitySharingDto = new ActivitySharingDto(null, SHARED_URL);

    when(findByTokenService.findUserByToken(USER_TOKEN)).thenReturn(user);
  }


  @Test
  public void getSharedURL_method_should_return_dto_with_url() throws Exception {
    when(activityService.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);
    when(participantService.getByRecruitmentNumber(RN)).thenReturn(participant);
    whenNew(ParticipantTempTokenRequestDto.class).withArguments(RN, USER_ID)
      .thenReturn(participantTempTokenRequestDto);
    when(temporaryParticipantTokenService.generateTempToken(participantTempTokenRequestDto))
      .thenReturn(PARTICIPANT_TOKEN);
    whenNew(ActivitySharing.class).withArguments(ACTIVITY_OID, USER_OID, PARTICIPANT_TOKEN)
      .thenReturn(activitySharing);
    when(activitySharingService.getSharedURL(activitySharing)).thenReturn(activitySharingDto);
    assertNotNull(
      activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN).getUrl()
    );
  }

  @Test(expected = HttpResponseException.class)
  public void getSharedURL_method_should_throw_exception_in_case_not_fill_activity() throws Exception {
    surveyActivity.setMode(ActivityMode.ONLINE);
    when(activityService.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);
    activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void getSharedURL_method_should_handle_DataNotFoundException() throws Exception {
    when(activityService.getByID(ACTIVITY_ID)).thenThrow(new DataNotFoundException());

    //when(activitySharingService.getSharedURL(activitySharing)).thenThrow(new DataNotFoundException());
    activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void getSharedURL_method_should_handle_ValidationException() throws Exception {
    when(activityService.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);
    when(participantService.getByRecruitmentNumber(RN)).thenReturn(participant);
    when(findByTokenService.findUserByToken(USER_TOKEN)).thenThrow(new ValidationException());
    activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void getSharedURL_method_should_handle_ParseException() throws Exception {
    when(activityService.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);
    when(participantService.getByRecruitmentNumber(RN)).thenReturn(participant);
    when(findByTokenService.findUserByToken(USER_TOKEN)).thenThrow(new ParseException("", 0));
    activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }

  @Test(expected = HttpResponseException.class)
  public void getSharedURL_method_should_handle_TokenException() throws Exception {
    when(activityService.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);
    when(participantService.getByRecruitmentNumber(RN)).thenReturn(participant);
    when(findByTokenService.findUserByToken(USER_TOKEN)).thenReturn(user);
    whenNew(ParticipantTempTokenRequestDto.class).withArguments(RN, USER_ID)
      .thenReturn(participantTempTokenRequestDto);
    when(temporaryParticipantTokenService.generateTempToken(participantTempTokenRequestDto))
      .thenThrow(new TokenException());
    activitySharingFacade.getSharedURL(ACTIVITY_ID, USER_TOKEN);
  }


  @Test
  public void renovateSharedURL_method_should_return_dto_with_url() throws Exception {
    mockActivitySharingInstanceForRenovateMethod();
    when(activitySharingService.renovateSharedURL(activitySharing)).thenReturn(activitySharingDto);
    assertNotNull(activitySharingFacade.renovateSharedURL(ACTIVITY_ID, USER_TOKEN).getUrl());
  }

  @Test(expected = HttpResponseException.class)
  public void renovateSharedURL_method_should_handle_DataNotFoundException() throws Exception {
    mockActivitySharingInstanceForRenovateMethod();
    when(activitySharingService.renovateSharedURL(activitySharing)).thenThrow(new DataNotFoundException());
    activitySharingFacade.renovateSharedURL(ACTIVITY_ID, USER_TOKEN);
  }


  @Test
  public void deleteSharedURL_method_should_return_url() throws Exception {
    mockInitializeForDeleteMethod();
    activitySharingFacade.deleteSharedURL(ACTIVITY_ID, USER_TOKEN);
    verify(activitySharingService, Mockito.times(1)).deleteSharedURL(ACTIVITY_ID);
  }

  @Test(expected = HttpResponseException.class)
  public void deleteSharedURL_method_should_handle_DataNotFoundException() throws Exception {
    mockInitializeForDeleteMethod();
    doThrow(new DataNotFoundException()).when(activitySharingService, "deleteSharedURL", ACTIVITY_ID);
    activitySharingFacade.deleteSharedURL(ACTIVITY_ID, USER_TOKEN);
  }


  private void mockActivitySharingInstanceForRenovateMethod() throws Exception {
    whenNew(ActivitySharing.class).withArguments(ACTIVITY_OID, null, null)
      .thenReturn(activitySharing);
  }

  private void mockInitializeForDeleteMethod() {
    try {
      when(activityService.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);
    }
    catch (Exception e){}
  }

}
