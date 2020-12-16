package org.ccem.otus.model.survey.activity.dto;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.doNothing;

@RunWith(PowerMockRunner.class)
public class StageSurveyActivitiesDtoTest {

  private static final ObjectId STAGE_OID = new ObjectId();
  private static final String STAGE_NAME = "some stage";

  private static final String ACRONYM = "ABC";
  private static final String SURVEY_NAME = "abc";

  private StageSurveyActivitiesDto stageSurveyActivitiesDto;
  private List<StageAcronymSurveyActivitiesDto> stageAcronymSurveyActivitiesDtos;
  private StageAcronymSurveyActivitiesDto stageAcronymSurveyActivitiesDto = PowerMockito.spy(new StageAcronymSurveyActivitiesDto(ACRONYM, SURVEY_NAME));

  @Before
  public void setUp() throws Exception {
    doNothing().when(stageAcronymSurveyActivitiesDto, "removeAcronymGroup");

    stageAcronymSurveyActivitiesDtos = new ArrayList<>();
    stageAcronymSurveyActivitiesDtos.add(stageAcronymSurveyActivitiesDto);

    stageSurveyActivitiesDto = new StageSurveyActivitiesDto();
    Whitebox.setInternalState(stageSurveyActivitiesDto, "stageId", STAGE_OID);
    Whitebox.setInternalState(stageSurveyActivitiesDto, "stageName", STAGE_NAME);
    Whitebox.setInternalState(stageSurveyActivitiesDto, "stageAcronymSurveyActivitiesDtos", stageAcronymSurveyActivitiesDtos);
  }

  @Test
  public void getters_test(){
    assertEquals(STAGE_OID, stageSurveyActivitiesDto.getStageId());
    assertEquals(stageAcronymSurveyActivitiesDtos, stageSurveyActivitiesDto.getStageAcronymSurveyActivitiesDtos());
  }

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel() {
    assertTrue(StageSurveyActivitiesDto.deserialize("{}") instanceof StageSurveyActivitiesDto);
  }


  @Test
  public void hasAcronyms_method_should_return_true(){
    assertTrue(stageSurveyActivitiesDto.hasAcronyms());
  }

  @Test
  public void hasAcronyms_method_should_return_false_in_case_null_stageAcronymSurveyActivitiesDtos(){
    Whitebox.setInternalState(stageSurveyActivitiesDto, "stageAcronymSurveyActivitiesDtos", (Object) null);
    assertFalse(stageSurveyActivitiesDto.hasAcronyms());
  }

  @Test
  public void hasAcronyms_method_should_return_false_in_case_empty_stageAcronymSurveyActivitiesDtos(){
    Whitebox.setInternalState(stageSurveyActivitiesDto, "stageAcronymSurveyActivitiesDtos", new ArrayList<>());
    assertFalse(stageSurveyActivitiesDto.hasAcronyms());
  }

  @Test
  public void format_method_should_invoke_removeAcronymGroup_method(){
    stageSurveyActivitiesDto.format();
    Mockito.verify(stageAcronymSurveyActivitiesDto, Mockito.times(1)).removeAcronymGroup();
  }

  @Test
  public void formatAndGetAcronymsNotInStageAvailableSurveys_method_return_list(){
    final String ACRONYM_OUT = "XYZ";
    List<String> stageAvailableSurveys = new ArrayList<>();
    stageAvailableSurveys.add(ACRONYM);
    stageAvailableSurveys.add(ACRONYM_OUT);

    List<String> acronymInStageAvailableSurveys =
      stageSurveyActivitiesDto.formatAndGetAcronymsNotInStageAvailableSurveys(STAGE_NAME, stageAvailableSurveys);

    assertEquals(1, acronymInStageAvailableSurveys.size());
    assertEquals(ACRONYM_OUT, acronymInStageAvailableSurveys.get(0));
  }

  @Test
  public void addAcronymWithNoActivities_method_test(){
    stageSurveyActivitiesDto.addAcronymWithNoActivities("", "");
    assertEquals(2, stageSurveyActivitiesDto.getStageAcronymSurveyActivitiesDtos().size());
  }
}
