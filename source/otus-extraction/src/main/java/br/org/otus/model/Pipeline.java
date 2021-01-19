package br.org.otus.model;

import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

public class Pipeline extends SerializableModelWithID {

  private ObjectId surveyId;
  private String Rscript;

  public ObjectId getSurveyId() {
    return surveyId;
  }

  public String getRscript() {
    return Rscript;
  }

  public String toJson(){
    return SerializableModelWithID.serialize(this);
  }

  public static Pipeline fromJson(String json){
    return (Pipeline)SerializableModelWithID.deserialize(json, Pipeline.class);
  }
}
