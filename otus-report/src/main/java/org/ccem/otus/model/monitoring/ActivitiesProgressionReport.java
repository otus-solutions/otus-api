package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public class ActivitiesProgressionReport {

  private Integer rn;
  private List<ActivityFlagReport> activities;


  public static String serialize(ActivitiesProgressionReport progressionReport) {
    return ActivitiesProgressionReport.getGsonBuilder().create().toJson(progressionReport);
  }

  public static ActivitiesProgressionReport deserialize(String progressionReportJson) throws ValidationException {
    ActivitiesProgressionReport progressionReport = ActivitiesProgressionReport.getGsonBuilder().create().fromJson(progressionReportJson, ActivitiesProgressionReport.class);
    return progressionReport;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();

    return builder;
  }
}
