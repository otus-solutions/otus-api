package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;
import org.ccem.otus.model.survey.activity.status.StatusDto;

public class SurveyActivityItemListDto extends SerializableModelWithID {

  private static final String OBJECT_TYPE = "ActivityBasicModel";

  @SerializedName("_id")
  private ObjectId id;

  private String objectType;
  private String acronym;
  private String name;
  private String category;
  private StatusDto lastStatus;
  private String externalID;


  public static SurveyActivityItemListDto deserialize(String json){
    return (SurveyActivityItemListDto)deserialize(json, SurveyActivityItemListDto.class);
  }
}
