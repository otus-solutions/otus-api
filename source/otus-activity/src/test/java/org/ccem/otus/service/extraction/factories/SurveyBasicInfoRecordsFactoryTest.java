package org.ccem.otus.service.extraction.factories;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.interview.Interview;
import org.ccem.otus.model.survey.activity.status.ActivityStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({SurveyBasicInfoRecordsFactory.class})
public class SurveyBasicInfoRecordsFactoryTest {
  @Mock
  private SurveyActivity surveyActivity;

  @Test
  public void method_getLastInterview_should_return_null_when_interview_dont_have_user() {
    PowerMockito.when(surveyActivity.getLastInterview()).thenReturn(null);
    Assert.assertNull(SurveyBasicInfoRecordsFactory.getLastInterviewer(surveyActivity));
  }

  @Test
  public void method_getCurrentStatusName_should_return_null_when_dont_exist_status() {
    Assert.assertNull(SurveyBasicInfoRecordsFactory.getCurrentStatusName(null));
  }

  @Test
  public void method_getCurrentStatusDate_should_return_null_when_don_exist_current_status() {
    Assert.assertNull(SurveyBasicInfoRecordsFactory.getCurrentStatusDate(null));
  }

  @Test
  public void method_getCreationDate_should_return_null_when_dont_exist_creation_status() {
    Assert.assertNull(SurveyBasicInfoRecordsFactory.getCreationDate(null));
  }

  @Test
  public void method_getPaperRealizationDate_should_return_null_when_dont_exist_paper_status() {
    Assert.assertNull(SurveyBasicInfoRecordsFactory.getPaperRealizationDate(null));
  }

  @Test
  public void method_getPaperInterviewer_should_return_null_when_dont_exist_user_in_status() {
    ActivityStatus activityStatus = PowerMockito.mock(ActivityStatus.class);
    Mockito.when(activityStatus.getUser()).thenReturn(null);
    Assert.assertNull(SurveyBasicInfoRecordsFactory.getPaperInterviewer(activityStatus));
  }

  @Test
  public void method_getLastFinalizedDate_should_return_null_when_finalized_status_dont_exist() {
    Assert.assertNull(SurveyBasicInfoRecordsFactory.getLasFinalizationDate(null));
  }

  @Test
  public void method_getRecruitmentNumber_should_return_null_when_recruitmentNumber_dont_exist() {
    PowerMockito.when(surveyActivity.getParticipantData()).thenReturn(null);
    Assert.assertNull(SurveyBasicInfoRecordsFactory.getRecruitmentNumber(surveyActivity));
  }

  @Test
  public void method_getCategory_should_return_null_when_category_dont_exist() {
    PowerMockito.when(surveyActivity.getCategory()).thenReturn(null);
    Assert.assertNull(SurveyBasicInfoRecordsFactory.getCategory(surveyActivity));
  }
}
