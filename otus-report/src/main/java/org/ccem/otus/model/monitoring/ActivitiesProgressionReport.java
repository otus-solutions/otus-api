package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;

import java.util.*;

public class ActivitiesProgressionReport {

  private Integer rn;
  private List<ActivityFlagReport> activities;

  public void normalize(HashMap<String, ActivityFlagReport> map) {
    for (ActivityFlagReport activity : activities) {
      map.put(activity.getAcronym(), activity);
    }

    this.activities = new ArrayList<>(map.values());

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

  public static String serialize(ActivitiesProgressionReport progressionReport) {
    return ActivitiesProgressionReport.getGsonBuilder().create().toJson(progressionReport);
  }

  public static ActivitiesProgressionReport deserialize(String progressionReportJson) {
    ActivitiesProgressionReport progressionReport = ActivitiesProgressionReport.getGsonBuilder().create().fromJson(progressionReportJson, ActivitiesProgressionReport.class);
    return progressionReport;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();

    return builder;
  }
}
