package br.org.otus.examUploader.business.extraction.model;

import java.util.List;

import com.google.gson.GsonBuilder;

public class ParticipantExamUploadRecordExtraction {

  private Long recruitmentNumber;
  private List<ParticipantExamUploadResultExtraction> results;

  public static String serialize(ParticipantExamUploadRecordExtraction progressionReport) {
    return ParticipantExamUploadRecordExtraction.getGsonBuilder().create().toJson(progressionReport);
  }

  public static ParticipantExamUploadRecordExtraction deserialize(String progressionReportJson) {
    GsonBuilder builder = ParticipantExamUploadRecordExtraction.getGsonBuilder();
    return builder.create().fromJson(progressionReportJson, ParticipantExamUploadRecordExtraction.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();

    return builder;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public List<ParticipantExamUploadResultExtraction> getResults() {
    return results;
  }

}
