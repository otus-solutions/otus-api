package org.ccem.otus.model.survey.activity.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class SurveyActivityItemListDtoTest {

  private static final String SURVEY_NAME = "abc";

  private SurveyActivityItemListDto surveyActivityItemListDto;

  @Before
  public void setUp(){
    surveyActivityItemListDto = new SurveyActivityItemListDto();
    Whitebox.setInternalState(surveyActivityItemListDto, "name", SURVEY_NAME);
  }

  @Test
  public void getters_test(){
    assertEquals(SURVEY_NAME, surveyActivityItemListDto.getName());
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel() {
    assertTrue(SurveyActivityItemListDto.deserialize("{}") instanceof SurveyActivityItemListDto);
  }
}
