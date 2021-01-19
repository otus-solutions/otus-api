package org.ccem.otus.service.extraction.model;

import com.google.gson.annotations.SerializedName;
import org.ccem.otus.model.SerializableModel;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.survey.form.SurveyForm;

public class ActivityExtraction extends SerializableModel {

  @SerializedName("survey")
  private ActivityExtractionSurveyData surveyData;
  @SerializedName("activity")
  private ActivityExtractionActivityData activityData;

  public ActivityExtraction(SurveyForm surveyForm, SurveyActivity surveyActivity) {
    this.surveyData = new ActivityExtractionSurveyData(surveyForm);
    this.activityData = new ActivityExtractionActivityData(surveyActivity);
  }

  public ActivityExtractionSurveyData getSurveyData() {
    return surveyData;
  }

  public ActivityExtractionActivityData getActivityData() {
    return activityData;
  }

  public void setParticipantData(Participant participant){
    this.activityData.setParticipantData(participant);
  }

  public String toJson(){
    return SerializableModel.serialize(this);
  }
}
