package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.ccem.otus.model.SerializableModel;

public class StageAcronymSurveyActivitiesDto extends SerializableModel {

  @SerializedName("_id")
  private AcronymGroup acronymGroup;

  private SurveyActivityItemListDto[] activities;

  private String acronym;

  @SerializedName("name")
  private String activityName;


  public static StageAcronymSurveyActivitiesDto deserialize(String json){
    StageAcronymSurveyActivitiesDto dto = (StageAcronymSurveyActivitiesDto)deserialize(json, StageAcronymSurveyActivitiesDto.class);
    dto.acronym = dto.acronymGroup.getAcronym();
    dto.acronymGroup = null;
    return dto;
  }

}
