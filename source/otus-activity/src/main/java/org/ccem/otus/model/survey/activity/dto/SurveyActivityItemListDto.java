package org.ccem.otus.model.survey.activity.dto;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;
import org.ccem.otus.model.survey.activity.SurveyActivity;
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

  public SurveyActivityItemListDto() {
    objectType = OBJECT_TYPE;
  }

  public SurveyActivityItemListDto(SurveyActivity surveyActivity) {
    objectType = OBJECT_TYPE;
    acronym = surveyActivity.getSurveyForm().getAcronym();
    name = surveyActivity.getSurveyForm().getName();
    category = surveyActivity.getCategory().getName();
    if(surveyActivity.getLastStatus().isPresent()){
      lastStatus = new StatusDto(surveyActivity.getLastStatus().get());
    }
    externalID = surveyActivity.getExternalID();
  }
}
