package org.ccem.otus.model.survey.activity.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class StageAcronymSurveyActivitiesDtoTest {

  private static final String ACRONYM = "ABC";
  private static final String ACRONYM_2 = "ABCX";
  private static final String SURVEY_NAME = "abc";
  private static final String SURVEY_NAME_2 = "def";

  private StageAcronymSurveyActivitiesDto stageAcronymSurveyActivitiesDto;

  @Before
  public void setUp(){
    stageAcronymSurveyActivitiesDto = new StageAcronymSurveyActivitiesDto(ACRONYM, SURVEY_NAME);
  }

  @Test
  public void getters_test(){
    assertEquals(ACRONYM, stageAcronymSurveyActivitiesDto.getAcronym());
    assertEquals(0, stageAcronymSurveyActivitiesDto.getActivities().size());
  }

  @Test
  public void setters_test(){
    stageAcronymSurveyActivitiesDto.setAcronym(ACRONYM_2);
    assertEquals(ACRONYM_2, stageAcronymSurveyActivitiesDto.getAcronym());

    stageAcronymSurveyActivitiesDto.setActivities(null);
    assertEquals(null, stageAcronymSurveyActivitiesDto.getActivities());
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel() {
    assertTrue(StageAcronymSurveyActivitiesDto.deserialize("{}") instanceof StageAcronymSurveyActivitiesDto);
  }

  @Test
  public void removeAcronymGroup_should_set_acronym_and_activityName_from_acronymGroup(){
    AcronymGroup acronymGroup = PowerMockito.spy(new AcronymGroup());
    Whitebox.setInternalState(acronymGroup, "acronym", ACRONYM_2);
    Whitebox.setInternalState(stageAcronymSurveyActivitiesDto, "acronymGroup", acronymGroup);

    SurveyActivityItemListDto surveyActivityItemListDto = new SurveyActivityItemListDto();
    Whitebox.setInternalState(surveyActivityItemListDto, "name", SURVEY_NAME_2);
    List<SurveyActivityItemListDto> activities = new ArrayList<>();
    activities.add(surveyActivityItemListDto);
    Whitebox.setInternalState(stageAcronymSurveyActivitiesDto, "activities", activities);
    assertEquals(activities, stageAcronymSurveyActivitiesDto.getActivities());

    stageAcronymSurveyActivitiesDto.removeAcronymGroup();
    assertEquals(ACRONYM_2, stageAcronymSurveyActivitiesDto.getAcronym());
  }
}
