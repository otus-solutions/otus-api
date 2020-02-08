package org.ccem.otus.model.survey.activity.status;

import com.google.gson.GsonBuilder;
import org.ccem.otus.model.survey.activity.User;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class ActivityStatus {

  private String objectType;
  private ActivityStatusOptions name;
  private LocalDateTime date;
  private User user;

  public String getObjectType() {
    return objectType;
  }

  public String getName() {
    return name.name();
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    if (user != null) return user;
    throw new UserNotFoundException();
  }

  public static String serialize(ActivityStatus activityStatus) {
    return ActivityStatus.getGsonBuilder().create().toJson(activityStatus);
  }

  public static ActivityStatus deserialize(String activityStatusJson) {
    ActivityStatus activityStatus = ActivityStatus.getGsonBuilder().create().fromJson(activityStatusJson, ActivityStatus.class);
    return activityStatus;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    return builder;
  }
}
