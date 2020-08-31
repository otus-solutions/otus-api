package org.ccem.otus.logs.activity;

import br.org.otus.utils.ObjectIdAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.logs.events.ActivitySharedLog;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class ActivityLog {
  private String action;
  private ObjectId userId;
  private LocalDateTime date;

  public ActivityLog(ActivitySharedLog activitySharedLog) {
    this.action = activitySharedLog.getActivitySharingProgressLog();
    this.userId = activitySharedLog.getObjectId();
    this.date = LocalDateTime.now();
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public ObjectId getUserId() {
    return userId;
  }

  public void setUserId(ObjectId userId) {
    this.userId = userId;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public static String serialize(ActivityLog activityLog) {
    return ActivityLog.getGsonBuilder().create().toJson(activityLog);
  }

  public static ActivityLog deserialize(String activityLog) {
    return ActivityLog.getGsonBuilder().create().fromJson(activityLog, ActivityLog.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    return builder;
  }
}