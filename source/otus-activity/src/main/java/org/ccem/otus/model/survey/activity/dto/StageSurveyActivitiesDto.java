package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

public class StageSurveyActivitiesDto extends SerializableModelWithID {

  @SerializedName("_id")
  private ObjectId stageID;

  private String stageName;

  private StageAcronymSurveyActivitiesDto[] acronyms;


  public ObjectId getStageID() {
    return stageID;
  }

  public StageAcronymSurveyActivitiesDto[] getAcronyms() {
    return acronyms;
  }

  public void setStageName(String stageName) {
    this.stageName = stageName;
  }

  public static StageSurveyActivitiesDto deserialize(String json){
    return (StageSurveyActivitiesDto) SerializableModelWithID.deserialize(json, StageSurveyActivitiesDto.class);
  }

}
