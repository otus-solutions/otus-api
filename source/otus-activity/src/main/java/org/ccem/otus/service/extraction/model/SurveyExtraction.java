package org.ccem.otus.service.extraction.model;

import org.ccem.otus.model.SerializableModelWithID;

public class SurveyExtraction extends SerializableModelWithID {

  private String surveyId;
  private String surveyAcronym;
  private Integer surveyVersion;
  private String RscriptName;

  public SurveyExtraction(String surveyAcronym, Integer surveyVesion, String rscriptName) {
    this.surveyAcronym = surveyAcronym;
    this.surveyVersion = surveyVesion;
    RscriptName = rscriptName;
  }

  public String getSurveyId() {
    return surveyId;
  }

  public String getSurveyAcronym() {
    return surveyAcronym;
  }

  public Integer getSurveyVersion() {
    return surveyVersion;
  }

  public String getRscriptName() {
    return RscriptName;
  }

  public void setSurveyId(String surveyId) {
    this.surveyId = surveyId;
    this.surveyAcronym = null;
    this.surveyVersion = null;
  }

  public static SurveyExtraction fromJson(String json){
    return (SurveyExtraction)deserialize(json, SurveyExtraction.class);
  }

}
