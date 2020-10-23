package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

import java.util.List;
import java.util.stream.Collectors;

public class StageSurveyActivitiesDto extends SerializableModelWithID {

  @SerializedName("_id")
  private ObjectId stageId;

  private String stageName;

  private List<StageAcronymSurveyActivitiesDto> acronyms;


  public ObjectId getStageId() {
    return stageId;
  }

  public List<StageAcronymSurveyActivitiesDto> getAcronyms() {
    return acronyms;
  }

  public void setStageName(String stageName) {
    this.stageName = stageName;
  }

  public static StageSurveyActivitiesDto deserialize(String json){
    return (StageSurveyActivitiesDto) SerializableModelWithID.deserialize(json, StageSurveyActivitiesDto.class);
  }


  public boolean hasAcronyms(){
    return (acronyms != null && !acronyms.isEmpty());
  }

  public void removeAcronymsWithoutActivities(){
    acronyms = acronyms.stream().filter(dto -> dto.hasActivities()).collect(Collectors.toList());

    for(StageAcronymSurveyActivitiesDto dto : acronyms){
      dto.removeAcronymGroup();
    }
  }

}
