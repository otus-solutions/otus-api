package org.ccem.otus.service.extraction.model;

import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.User;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.model.survey.activity.filling.FillContainer;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.ccem.otus.model.survey.activity.mode.ActivityMode;
import org.ccem.otus.model.survey.activity.navigation.NavigationTracker;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.survey.form.SurveyForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class ActivityExtractionActivityDataTest {

  private static final String ACTIVITY_ID = "5e0658135b4ff40f8916d2b5";
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final String ACRONYM = "ANTC";
  private static final Integer VERSION = 1;
  private static final ActivityMode MODE = ActivityMode.ONLINE;
  private static final String CATEGORY_NAME = "cat";
  private static final ActivityCategory CATEGORY = new ActivityCategory(CATEGORY_NAME, "", null, null);
  private static final String ACTIVITY_CENTER_ACRONYM = "RS";
  private static final List<QuestionFill> FILLING_LIST = new ArrayList<>();
  private static final String USER_EMAIL = "fulano@gmail.com";
  private static final String EXTERNAL_ID = "123";
  private static final LocalDateTime CURR_STATUS_DATE = LocalDateTime.now();

  private ActivityExtractionActivityData activityExtractionActivityData;

  @Mock
  private SurveyActivity surveyActivity;
  @Mock
  private SurveyForm surveyForm;
  @Mock
  private Participant participant;
  @Mock
  private FieldCenter fieldCenter;
  @Mock
  private ActivityStatus activityStatus;
  @Mock
  private ActivityStatus creationActivityStatus;
  @Mock
  private ActivityStatus finalizeActivityStatus;
  @Mock
  private User userEmail;
  @Mock
  private FillContainer fillContainer;
  @Mock
  private NavigationTracker navigationTracker;

  @Before
  public void setUp(){
    doReturn(new ObjectId(ACTIVITY_ID)).when(surveyActivity).getActivityID();

    doReturn(ACRONYM).when(surveyForm).getAcronym();
    doReturn(VERSION).when(surveyForm).getVersion();
    doReturn(surveyForm).when(surveyActivity).getSurveyForm();

    doReturn(MODE).when(surveyActivity).getMode();
    doReturn(CATEGORY).when(surveyActivity).getCategory();
    doReturn(USER_EMAIL).when(userEmail).getEmail();

    doReturn(ActivityStatusOptions.CREATED.getName()).when(activityStatus).getName();
    doReturn(userEmail).when(activityStatus).getUser();
    doReturn(CURR_STATUS_DATE).when(activityStatus).getDate();
    doReturn(CURR_STATUS_DATE).when(creationActivityStatus).getDate();

    doReturn(Optional.of(activityStatus)).when(surveyActivity).getCurrentStatus();
    doReturn(Optional.of(activityStatus)).when(surveyActivity).getLastStatus();
    doReturn(creationActivityStatus).when(surveyActivity).getCreationStatus();

    doReturn(Optional.of(finalizeActivityStatus)).when(surveyActivity).getLastStatusByName(ActivityStatusOptions.FINALIZED.getName());
    doReturn(ActivityStatusOptions.FINALIZED.getName()).when(finalizeActivityStatus).getName();
    doReturn(CURR_STATUS_DATE).when(finalizeActivityStatus).getDate();

    doReturn(EXTERNAL_ID).when(surveyActivity).getExternalID();

    doReturn(fillContainer).when(surveyActivity).getFillContainer();
    doReturn(FILLING_LIST).when(fillContainer).getFillingList();

    doReturn(participant).when(surveyActivity).getParticipantData();
    doReturn(RECRUITMENT_NUMBER).when(participant).getRecruitmentNumber();
    doReturn(fieldCenter).when(participant).getFieldCenter();
    doReturn(ACTIVITY_CENTER_ACRONYM).when(fieldCenter).getAcronym();

    navigationTracker.items = new ArrayList<>();
    doReturn(navigationTracker).when(surveyActivity).getNavigationTracker();

    activityExtractionActivityData = new ActivityExtractionActivityData(surveyActivity);
  }

  @Test
  public void getters_check(){
    assertEquals(ACTIVITY_ID, activityExtractionActivityData.getId());
    assertEquals(RECRUITMENT_NUMBER, activityExtractionActivityData.getRecruitmentNumber());
  }

  @Test
  public void setParticipantData_should_set_participantFieldCenter(){
    activityExtractionActivityData.setParticipantData(participant);
    Mockito.verify(participant, Mockito.times(2)).getFieldCenter();
  }
}
