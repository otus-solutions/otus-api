package org.ccem.otus.model.survey.activity.permission;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import com.google.gson.GsonBuilder;

public class ActivityAccessPermission {

  private ObjectId _id;
  private String objectType;
  private Integer version;
  private String acronym;
  private List<String> exclusiveDisjunction;

  public ObjectId getId() {
    return _id;
  }

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
    return ActivityAccessPermission.getGsonBuilder().create().fromJson(activityAccessPermission, ActivityAccessPermission.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }
}
