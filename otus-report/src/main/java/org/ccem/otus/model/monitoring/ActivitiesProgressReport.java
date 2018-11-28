package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;
import org.ccem.otus.utils.LongAdapter;

import java.util.*;

public class ActivitiesProgressReport {

  //TODO 28/11/18: review this Long
  private Long rn;
  private LinkedList<ActivityFlagReport> activities;
  private LinkedList<String> acronyms;

  public void normalize(LinkedList<String> pattern) {
    LinkedList<ActivityFlagReport> temp = new LinkedList<>();

    acronyms = pattern;

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

  public LinkedList<ActivityFlagReport> getActivities() {
    return activities;
  }

  public LinkedList<String> getAcronyms() {
    return acronyms;
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

  class ActivityFlagReport {

    private Long rn;
    private String acronym;
    private Integer status;

    public ActivityFlagReport(String acronym) {
      this.acronym = acronym;
      this.status = null;
    }

    public ActivityFlagReport(Long rn, String acronym) {
      this.rn = rn;
      this.acronym = acronym;
    }

    public String getAcronym() {
      return acronym;
    }

    public Integer getStatus() {
      return status;
    }

    public void setRn(Long rn) {
      this.rn = rn;
    }

    public void setStatus(Integer status) {
      this.status = status;
    }
  }
}
