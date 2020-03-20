package org.ccem.otus.model.survey.offlineActivity;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.AnswerAdapter;
import org.ccem.otus.utils.GeoJson;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;
import java.util.List;

public class OfflineActivityCollection {
  private ObjectId _id;
  private String observation;
  private ObjectId userId;
  private LocalDateTime date;
  private List<SurveyActivity> activities;
  private GeoJson geoJson;
  private Boolean availableToSynchronize;


  public String getObservation() {
    return observation;
  }

  public ObjectId get_id() {
    return _id;
  }

  public ObjectId getUserId() {
    return userId;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public static OfflineActivityCollection deserialize(String surveyActivities) {
    GsonBuilder builder = getGsonBuilder();
    return builder.create().fromJson(surveyActivities, OfflineActivityCollection.class);
  }

  public static String serializeToJsonString(OfflineActivityCollection offlineActivityCollection) {
    return getGsonBuilder().create().toJson(offlineActivityCollection);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = SurveyForm.getGsonBuilder();

    builder.registerTypeAdapter(AnswerFill.class, new AnswerAdapter());
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.serializeNulls();

    return builder;
  }

  public void setUserId(ObjectId userId) {
    this.userId = userId;
  }

  public void setAvailableToSynchronize(Boolean availableToSynchronize) {
    this.availableToSynchronize = availableToSynchronize;
  }

  public List<SurveyActivity> getActivities() {
    return activities;
  }

  public Boolean getAvailableToSynchronize() {
    return availableToSynchronize;
  }
}
