package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

public class StageSurveyActivitiesDto extends SerializableModelWithID {

  @SerializedName("_id")
  private ObjectId stageID;

  private StageAcronymSurveyActivitiesDto[] acronyms;

  private String stageName;


  public static StageSurveyActivitiesDto deserialize(String json){
    System.out.println(json);
    return (StageSurveyActivitiesDto) SerializableModelWithID.deserialize(json, StageSurveyActivitiesDto.class);
  }

}
