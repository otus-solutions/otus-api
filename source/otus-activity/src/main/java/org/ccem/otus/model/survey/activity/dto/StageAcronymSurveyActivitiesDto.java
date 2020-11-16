package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.ccem.otus.model.SerializableModel;

import java.util.ArrayList;
import java.util.List;

public class StageAcronymSurveyActivitiesDto extends SerializableModel {

  @SerializedName("_id")
  private AcronymGroup acronymGroup;
  private String acronym;
  @SerializedName("name")
  private String activityName;
  private List<SurveyActivityItemListDto> activities;


  public StageAcronymSurveyActivitiesDto(){}

  public StageAcronymSurveyActivitiesDto(String acronym, String activityName){
    this.acronym = acronym;
    this.activityName = activityName;
    this.activities = new ArrayList<>();
  }


  public String getAcronym() {
    return acronym;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public List<SurveyActivityItemListDto> getActivities() {
    return activities;
  }

  public void setActivities(List<SurveyActivityItemListDto> activities) {
    this.activities = activities;
  }

  public static StageAcronymSurveyActivitiesDto deserialize(String json){
    return (StageAcronymSurveyActivitiesDto)deserialize(json, StageAcronymSurveyActivitiesDto.class);
  }

  public void removeAcronymGroup(){
    acronym = acronymGroup.getAcronym();
    acronymGroup = null;
    activityName = activities.get(0).getName();
  }

}
