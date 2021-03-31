package org.ccem.otus.service.extraction.model;

import com.google.gson.annotations.SerializedName;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.item.SurveyItem;

import java.util.List;

public class ActivityExtractionSurveyData {

  @SerializedName("id")
  private String surveyId;
  private List<SurveyItem> itemContainer;

  public ActivityExtractionSurveyData(SurveyForm surveyForm) {
    this.surveyId = surveyForm.getSurveyID().toHexString();
    this.itemContainer = surveyForm.getSurveyTemplate().itemContainer;
  }

  public String getId() {
    return surveyId;
  }
}
