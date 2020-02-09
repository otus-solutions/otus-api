package org.ccem.otus.model.dataSources.activity;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
public class ActivityReportAnswerFillingDataSourceTest {

  private QuestionFill questionFill;

  private ActivityReportAnswerFillingDataSource activityReportAdd;

  private ArrayList<QuestionFill> result;

  @Mock
  private SurveyActivity surveyActivity;

  @Before
  public void setUp() throws Exception {
    activityReportAdd = spy(new ActivityReportAnswerFillingDataSource());
    questionFill = new QuestionFill();
    surveyActivity = new SurveyActivity();
    result = new ArrayList<QuestionFill>();
  }

  @Test
  public void method_addResult() throws Exception {
    activityReportAdd.addResult(questionFill);
    verifyPrivate(activityReportAdd, times(1)).invoke("addResult", questionFill);
  }

  @Test
  public void method_check_getResult_and_setResult() {
    activityReportAdd.setResult(result);
    assertEquals(result, activityReportAdd.getResult());
  }

}