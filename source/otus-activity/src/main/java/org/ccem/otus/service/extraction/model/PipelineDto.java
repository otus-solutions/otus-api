package org.ccem.otus.service.extraction.model;

import com.google.gson.annotations.SerializedName;
import org.ccem.otus.model.SerializableModelWithID;
import org.ccem.otus.survey.form.SurveyForm;

public class PipelineDto extends SerializableModelWithID {

  @SerializedName("survey")
  private SurveyForm surveyForm;
  private String Rscript;

  public SurveyForm getSurveyForm() {
    return surveyForm;
  }

  public String getRscript() {
    return Rscript;
  }

  public String toJson(){
    return serialize(this);
  }

  public static PipelineDto fromJson(String json){
    return (PipelineDto)deserialize(json, PipelineDto.class);
  }
}
