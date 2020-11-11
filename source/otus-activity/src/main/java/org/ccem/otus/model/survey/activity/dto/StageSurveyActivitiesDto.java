package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StageSurveyActivitiesDto extends SerializableModelWithID {

  @SerializedName("_id")
  private ObjectId stageId;

  private String stageName;

  @SerializedName("acronyms")
  private List<StageAcronymSurveyActivitiesDto> stageAcronymSurveyActivitiesDtos;


  public ObjectId getStageId() {
    return stageId;
  }

  public List<StageAcronymSurveyActivitiesDto> getStageAcronymSurveyActivitiesDtos() {
    return stageAcronymSurveyActivitiesDtos;
  }

  public void setStageName(String stageName) {
    this.stageName = stageName;
  }

  public static StageSurveyActivitiesDto deserialize(String json){
    return (StageSurveyActivitiesDto) SerializableModelWithID.deserialize(json, StageSurveyActivitiesDto.class);
  }


  public boolean hasAcronyms(){
    return (stageAcronymSurveyActivitiesDtos != null && !stageAcronymSurveyActivitiesDtos.isEmpty());
  }

  public void removeAcronymsWithoutActivities(){
    stageAcronymSurveyActivitiesDtos = stageAcronymSurveyActivitiesDtos.stream().filter(dto -> dto.hasActivities()).collect(Collectors.toList());

    for(StageAcronymSurveyActivitiesDto dto : stageAcronymSurveyActivitiesDtos){
      dto.removeAcronymGroup();
    }
  }

  public void addAcronymWithNoActivity(String acronym, String activityName){
    StageAcronymSurveyActivitiesDto acronymSurveyActivitiesDto = new StageAcronymSurveyActivitiesDto();
    acronymSurveyActivitiesDto.setAcronym(acronym);
    acronymSurveyActivitiesDto.setActivityName(activityName);
    acronymSurveyActivitiesDto.setActivities(new ArrayList<>());
    stageAcronymSurveyActivitiesDtos.add(acronymSurveyActivitiesDto);
  }

}
