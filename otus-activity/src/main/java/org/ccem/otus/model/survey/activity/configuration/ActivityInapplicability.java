package org.ccem.otus.model.survey.activity.configuration;

import com.google.gson.GsonBuilder;

public class ActivityInapplicability {

  private Long recruitmentNumber;
  private String acronym;
  private String observation;

  public static String serialize(ActivityInapplicability reportTemplate) {
    return ActivityInapplicability.getGsonBuilder().create().toJson(reportTemplate);
  }

  public static ActivityInapplicability deserialize(String reportTemplateJson) {
    ActivityInapplicability activityInapplicability = ActivityInapplicability.getGsonBuilder().create().fromJson(reportTemplateJson, ActivityInapplicability.class);
    return activityInapplicability;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();
    return builder;
  }

  public Long getRecruitmentNumber() {
    return this.recruitmentNumber;
  }

  public String getAcronym() {
    return acronym;
  }
}
