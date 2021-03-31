package org.ccem.otus.service.extraction.model;

import org.bson.types.ObjectId;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
import org.ccem.otus.survey.template.item.SurveyItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
public class ActivityExtractionSurveyDataTest {

  private static final String SURVEY_ID = "5e0658135b4ff40f8916d2b5";
  private static final List<SurveyItem> ITEM_CONTAINER = new ArrayList<>();

  private ActivityExtractionSurveyData activityExtractionSurveyData;

  @Mock
  private SurveyForm surveyForm;
  @Mock
  private SurveyTemplate surveyTemplate;

  @Before
  public void setUp(){
    surveyTemplate.itemContainer = ITEM_CONTAINER;

    doReturn(new ObjectId(SURVEY_ID)).when(surveyForm).getSurveyID();
    doReturn(surveyTemplate).when(surveyForm).getSurveyTemplate();

    activityExtractionSurveyData = new ActivityExtractionSurveyData(surveyForm);
  }

  @Test
  public void getters_check(){
    assertEquals(SURVEY_ID, activityExtractionSurveyData.getId());
  }
}
