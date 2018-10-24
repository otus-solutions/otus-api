package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class ActivitiesProgressReport {

  private Integer rn;
  private List<ActivityFlagReport> activities;

  public void normalize(LinkedList<String> pattern) {
    LinkedList<ActivityFlagReport> temp = new LinkedList<>();

    pattern.stream().forEach(acronym -> {
      temp.add(
        activities.stream()
          .filter(act -> act.getAcronym().equals(acronym))
          .findFirst()
          .orElse(new ActivityFlagReport(this.rn, acronym))
      );
    });

    this.activities = temp;
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
