package org.ccem.otus.model.survey.activity.dto;

import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

public class AcronymGroup extends SerializableModelWithID {

  private ObjectId stageID;
  private String acronym;

  public String getAcronym() {
    return acronym;
  }
}
