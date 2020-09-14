package org.ccem.otus.model.survey.activity.permission;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import com.google.gson.GsonBuilder;

public class ActivityAccessPermission {

  public static final String OBJECT_TYPE = "ActivityPermission";

  private ObjectId _id;
  private String objectType;
  private Integer version;
  private String acronym;
  private List<String> exclusiveDisjunction;

  public ActivityAccessPermission() {
    this.objectType = OBJECT_TYPE;
  }

  public ActivityAccessPermission(String acronym, Integer version) {
    this.objectType = OBJECT_TYPE;
    this.version = version;
    this.acronym = acronym;
  }

  public ObjectId getId() {
    return _id;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public String getAcronym() {
    return acronym;
  }

  public List<String> getExclusiveDisjunction() {
    return exclusiveDisjunction;
  }

  public void setExclusiveDisjunction(List<String> exclusiveDisjunction) {
    this.exclusiveDisjunction = exclusiveDisjunction;
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
