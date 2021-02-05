package org.ccem.otus.service.extraction.model;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.survey.form.SurveyForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivityExtraction.class, ActivityExtractionSurveyData.class})
public class ActivityExtractionTest {

  private ActivityExtraction activityExtraction;

  @Mock
  private ActivityExtractionSurveyData surveyData;
  @Mock
  private ActivityExtractionActivityData activityData;

  @Mock
  private SurveyForm surveyForm;
  @Mock
  private SurveyActivity surveyActivity;
  @Mock
  private Participant participant;


  @Before
  public void setUp() throws Exception {
    whenNew(ActivityExtractionSurveyData.class).withAnyArguments().thenReturn(surveyData);
    whenNew(ActivityExtractionActivityData.class).withAnyArguments().thenReturn(activityData);
    activityExtraction = new ActivityExtraction(surveyForm, surveyActivity);
  }

  @Test
  public void getters_check(){
    assertEquals(surveyData, activityExtraction.getSurveyData());
    assertEquals(activityData, activityExtraction.getActivityData());
  }

  @Test
  public void setParticipantData_should_call_activityData_setParticipantData_method(){
    activityExtraction.setParticipantData(participant);
    Mockito.verify(activityData, Mockito.times(1)).setParticipantData(participant);
  }
}
