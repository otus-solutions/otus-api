package org.ccem.otus.model.survey.jumpMap;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class SurveyJumpMapTest {
  private static final String SURVEY_JUMP_MAP_STRING = "{\"surveyOid\":{\"$oid\":\"5cc33fff99e0c9005f23c00e\"},\"surveyAcronym\":\"TST\",\"surveyVersion\":28,\"jumpMap\":{\"BEGIN NODE\":{\"possibleOrigins\":null,\"defaultDestination\":\"TST1\",\"alternativeDestinations\":[]},\"TST2\":{\"possibleOrigins\":{\"TST1\":false},\"defaultDestination\":\"END NODE\",\"alternativeDestinations\":[]},\"TST1\":{\"possibleOrigins\":{\"BEGIN NODE\":false},\"defaultDestination\":\"END NODE\",\"alternativeDestinations\":[{\"routeConditions\":[{\"extents\":\"StudioObject\",\"objectType\":\"RouteCondition\",\"name\":\"ROUTE_CONDITION_0\",\"rules\":[{\"extents\":\"SurveyTemplateObject\",\"objectType\":\"Rule\",\"when\":\"TST1\",\"operator\":\"equal\",\"answer\":\"1\",\"isMetadata\":false}]}],\"destination\":\"TST2\"}]}}}";

  @Test
  public void setValidJump_should_validate_question_ump() {
    SurveyJumpMap surveyJumpMap = SurveyJumpMap.deserialize(SURVEY_JUMP_MAP_STRING);
    surveyJumpMap.setValidJump("TST1", "TST2");
    assertEquals("TST1", surveyJumpMap.getValidOrigin("TST2"));
  }

  @Test
  public void getDefaultDestination_should_return_default_destination() {
    SurveyJumpMap surveyJumpMap = SurveyJumpMap.deserialize(SURVEY_JUMP_MAP_STRING);
    ArrayList<SurveyJumpMap.AlternativeDestination> alternativeDestination = surveyJumpMap.getQuestionAlternativeRoutes("TST1");
    assertEquals("TST2", alternativeDestination.get(0).getDestination());
    assertEquals("RouteCondition", alternativeDestination.get(0).getRouteConditions().get(0).objectType);
  }

  @Test
  public void getQuestionAlternativeRoutes_should_return_array_of_conditions() {
    SurveyJumpMap surveyJumpMap = SurveyJumpMap.deserialize(SURVEY_JUMP_MAP_STRING);
    assertEquals("END NODE", surveyJumpMap.getDefaultDestination("TST1"));
  }

  @Test
  public void validateDefaultJump_should_return_default_destination() {
    SurveyJumpMap surveyJumpMap = SurveyJumpMap.deserialize(SURVEY_JUMP_MAP_STRING);
    surveyJumpMap.validateDefaultJump("BEGIN NODE");
    assertEquals("BEGIN NODE", surveyJumpMap.getValidOrigin("TST1"));
  }

  @Test
  public void deserialize_should_return_SurveyJumpMap_instance() {
    SurveyJumpMap deserialize = SurveyJumpMap.deserialize(SURVEY_JUMP_MAP_STRING);
    assertEquals(SURVEY_JUMP_MAP_STRING, SurveyJumpMap.serialize(deserialize));
  }
}
