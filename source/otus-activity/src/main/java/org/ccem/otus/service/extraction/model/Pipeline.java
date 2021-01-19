package org.ccem.otus.service.extraction.model;

import org.ccem.otus.model.SerializableModel;

public class Pipeline extends SerializableModel {

  private String surveyId;
  private String Rscript;

  public Pipeline(String surveyId, String rscript) {
    this.surveyId = surveyId;
    this.Rscript = rscript;
  }

  public String getSurveyId() {
    return surveyId;
  }

  public String getRscript() {
    return Rscript;
  }

  public String toJson(){
    return serialize(this);
  }

  public static Pipeline fromJson(String json){
    return (Pipeline)deserialize(json, Pipeline.class);
  }
}
