package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;

import java.util.*;

public class ActivitiesProgressReport {

  private Integer rn;
  private List<ActivityFlagReport> activities;

  public void normalize(HashMap<String, ActivityFlagReport> normalizerMap) {
    for (ActivityFlagReport activity : activities) {
      normalizerMap.put(activity.getAcronym(), activity);
    }

    this.activities = new ArrayList<>(normalizerMap.values());

    for (ActivityFlagReport activity : this.activities) {
      activity.setRn(this.rn);
    }
  }

  public Integer getRn() {
    return rn;
  }

  public List<ActivityFlagReport> getActivities() {
    return activities;
  }

  public static String serialize(ActivitiesProgressReport progressionReport) {
    return ActivitiesProgressReport.getGsonBuilder().create().toJson(progressionReport);
  }

  public static ActivitiesProgressReport deserialize(String progressionReportJson) {
    return ActivitiesProgressReport.getGsonBuilder().create().fromJson(progressionReportJson, ActivitiesProgressReport.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();

    return builder;
  }
}
