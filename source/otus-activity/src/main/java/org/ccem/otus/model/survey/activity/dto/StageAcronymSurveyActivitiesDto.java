package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.ccem.otus.model.SerializableModel;

import java.util.List;

public class StageAcronymSurveyActivitiesDto extends SerializableModel {

  @SerializedName("_id")
  private AcronymGroup acronymGroup;

  private String acronym;

  @SerializedName("name")
  private String activityName;

  private List<SurveyActivityItemListDto> activities;


  public static StageAcronymSurveyActivitiesDto deserialize(String json){
    return (StageAcronymSurveyActivitiesDto)deserialize(json, StageAcronymSurveyActivitiesDto.class);
  }

  public boolean hasActivities(){
    return !activities.isEmpty();
  }

  public void removeAcronymGroup(){
    acronym = acronymGroup.getAcronym();
    acronymGroup = null;
    activityName = activities.get(0).getName();
  }

}
