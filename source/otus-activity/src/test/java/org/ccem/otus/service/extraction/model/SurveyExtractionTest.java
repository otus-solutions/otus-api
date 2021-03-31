package org.ccem.otus.service.extraction.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class SurveyExtractionTest {

  private static final String ACRONYM = "ANTC";
  private static final Integer VERSION = 1;
  private static final String SURVEY_ID = "5e0658135b4ff40f8916d2b5";
  private static final String R_SCRIPT_NAME = "some_script";
  private static final String JSON = "{}";

  private SurveyExtraction surveyExtraction;

  @Before
  public void setUp(){
    surveyExtraction = new SurveyExtraction(ACRONYM, VERSION, R_SCRIPT_NAME);
  }

  @Test
  public void getters_check(){
    assertEquals(null, surveyExtraction.getSurveyId());
    assertEquals(ACRONYM, surveyExtraction.getSurveyAcronym());
    assertEquals(VERSION, surveyExtraction.getSurveyVersion());
    assertEquals(R_SCRIPT_NAME, surveyExtraction.getRscriptName());
  }

  @Test
  public void setSurveyId_should_set_acronym_and_version_as_null(){
    surveyExtraction.setSurveyId(SURVEY_ID);
    assertEquals(SURVEY_ID, surveyExtraction.getSurveyId());
    assertEquals(null, surveyExtraction.getSurveyAcronym());
    assertEquals(null, surveyExtraction.getSurveyVersion());
  }

  @Test
  public void fromJson_should_return_SurveyExtraction_instance(){
    assertTrue(SurveyExtraction.fromJson(JSON) instanceof SurveyExtraction);
  }
}
