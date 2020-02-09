package org.ccem.otus.model.dataSources.activity;

import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.QuestionFill;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
public class AnswerFillingDataSourceTest {
  private QuestionFill questionFill;

  private AnswerFillingDataSource answerFillingDataSourceAdd;

  private ArrayList<QuestionFill> result;

  @Mock
  private AnswerFillingDataSourceFilters filters;

  @Mock
  private SurveyActivity surveyActivity;

  @Before
  public void setUp() throws Exception {
    answerFillingDataSourceAdd = spy(new AnswerFillingDataSource());
    questionFill = new QuestionFill();
    surveyActivity = new SurveyActivity();
    result = new ArrayList<QuestionFill>();
  }

  @Test
  public void method_addResult() throws Exception {
    answerFillingDataSourceAdd.addResult(questionFill);
    verifyPrivate(answerFillingDataSourceAdd, times(1)).invoke("addResult", questionFill);
  }

  @Test
  public void method_getFilters() {
    Whitebox.setInternalState(filters, "acronym", "ACTA");
    Whitebox.setInternalState(filters, "version", 2);
    Whitebox.setInternalState(filters, "category", "C0");

    PowerMockito.when(answerFillingDataSourceAdd.getFilters()).thenReturn(filters);

    assertEquals(filters, answerFillingDataSourceAdd.getFilters());
  }

  @Test
  public void method_check_getResult_and_setResult() {
    answerFillingDataSourceAdd.setResult(result);
    assertEquals(result, answerFillingDataSourceAdd.getResult());
  }

}