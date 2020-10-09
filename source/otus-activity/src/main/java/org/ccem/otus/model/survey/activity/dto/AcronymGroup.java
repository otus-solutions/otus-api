package org.ccem.otus.model.survey.activity.dto;

import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

public class AcronymGroup extends SerializableModelWithID {

  private ObjectId stageID;
  private String acronym;

  public String getAcronym() {
    return acronym;
  }

  public static AcronymGroup deserialize(String json){
    return (AcronymGroup)deserialize(json, AcronymGroup.class);
  }
}
