package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

import java.util.ArrayList;
import java.util.Collections;
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


  public static StageSurveyActivitiesDto deserialize(String json){
    return (StageSurveyActivitiesDto) SerializableModelWithID.deserialize(json, StageSurveyActivitiesDto.class);
  }


  public boolean hasAcronyms(){
    return (stageAcronymSurveyActivitiesDtos != null && !stageAcronymSurveyActivitiesDtos.isEmpty());
  }

  public void format(){
    for(StageAcronymSurveyActivitiesDto dto : stageAcronymSurveyActivitiesDtos){
      dto.removeAcronymGroup();
    }
  }

  public List<String> formatAndGetAcronymsNotInStageAvailableSurveys(String stageName, List<String> stageAvailableSurveys){
    this.stageName = stageName;
    List<String> acronymInStageAvailableSurveys = new ArrayList<>();

    for(StageAcronymSurveyActivitiesDto dto : stageAcronymSurveyActivitiesDtos){
      dto.removeAcronymGroup();
      if(stageAvailableSurveys.contains(dto.getAcronym())){
        acronymInStageAvailableSurveys.add(dto.getAcronym());
      }
    }

    return stageAvailableSurveys.stream()
      .filter(x -> !acronymInStageAvailableSurveys.contains(x))
      .collect(Collectors.toList());
  }

  public void addAcronymWithNoActivities(String acronym, String activityName){
    stageAcronymSurveyActivitiesDtos.add(new StageAcronymSurveyActivitiesDto(acronym, activityName));
  }

}
