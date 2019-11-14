package org.ccem.otus.service.extraction.model;

import org.ccem.otus.utils.LongAdapter;

import com.google.gson.GsonBuilder;

public class ActivityProgressResultExtraction {

  private Long rn;
  private String acronym;
  private String status;
  private String statusDate;
  private String observation;

  public static String serialize(ActivityProgressResultExtraction record) {
    return ActivityProgressResultExtraction.getGsonBuilder().create().toJson(record);
  }

  public static ActivityProgressResultExtraction deserialize(String json) {
    GsonBuilder builder = ActivityProgressResultExtraction.getGsonBuilder();
    return builder.create().fromJson(json, ActivityProgressResultExtraction.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    builder.serializeNulls();
    return builder;
  }

  public Long getRecruitmentNumber() {
    return this.rn;
  }

  public String getAcronym() {
    return this.acronym;
  }

  public String getStatus() {
    return this.status;
  }

  public String getStatusDate() {
    return statusDate;
  }

  public String getInapplicabilityObservation() {
    return observation;
  }

}