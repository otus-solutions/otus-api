package br.org.otus.laboratory.extraction.model;

import java.util.List;

import com.google.gson.GsonBuilder;

public class LaboratoryRecordExtraction implements Comparable<LaboratoryRecordExtraction> {

  private Long recruitmentNumber;
  private List<LaboratoryResultExtraction> results;

  public static String serialize(LaboratoryRecordExtraction progressionReport) {
    return LaboratoryRecordExtraction.getGsonBuilder().create().toJson(progressionReport);
  }

  public static LaboratoryRecordExtraction deserialize(String progressionReportJson) {
    GsonBuilder builder = LaboratoryRecordExtraction.getGsonBuilder();
    return builder.create().fromJson(progressionReportJson, LaboratoryRecordExtraction.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();

    return builder;
  }

  @Override
  public int compareTo(LaboratoryRecordExtraction record) {
    if (this.getRecruitmentNumber() < record.getRecruitmentNumber()) {
      return -1;
    }
    if (this.getRecruitmentNumber() > record.getRecruitmentNumber()) {
      return 1;
    }
    return 0;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public List<LaboratoryResultExtraction> getResults() {
    return results;
  }

}
