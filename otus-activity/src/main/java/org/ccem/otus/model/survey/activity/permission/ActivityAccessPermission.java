package org.ccem.otus.model.survey.activity.permission;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.utils.AnswerAdapter;
import org.ccem.otus.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import com.google.gson.GsonBuilder;

public class ActivityAccessPermission {
  
  private String objectType;  
  private Integer version;
  private String acronym;
  private List<String> exclusiveDisjunction;  
  
  public Integer getVersion() {
    return version;
  }
  public String getAcronym() {
    return acronym;
  }
  public List<String> getExclusiveDisjunction() {
    return exclusiveDisjunction;
  }
  
  public static String serialize(ActivityAccessPermission activityAccessPermission) {
    return getGsonBuilder().create().toJson(activityAccessPermission);
}

public static ActivityAccessPermission deserialize(String activityAccessPermission) {
    GsonBuilder builder = getGsonBuilder();   
    return builder.create().fromJson(activityAccessPermission, ActivityAccessPermission.class);
}

public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();    
    return builder;
    }
}
