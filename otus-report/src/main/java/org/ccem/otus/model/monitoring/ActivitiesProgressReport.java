package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;
import org.ccem.otus.utils.LongAdapter;

import java.util.*;
import java.util.stream.Collectors;

public class ActivitiesProgressReport {

  private Long rn;
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

  public Long getRn() {
    return rn;
  }

  public List<ActivityFlagReport> getActivities() {
    return activities;
  }

  public static String serialize(ActivitiesProgressReport progressionReport) {
    return ActivitiesProgressReport.getGsonBuilder().create().toJson(progressionReport);
  }

  public static ActivitiesProgressReport deserialize(String progressionReportJson) {
    GsonBuilder builder = ActivitiesProgressReport.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(progressionReportJson, ActivitiesProgressReport.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();

    return builder;
  }
}
