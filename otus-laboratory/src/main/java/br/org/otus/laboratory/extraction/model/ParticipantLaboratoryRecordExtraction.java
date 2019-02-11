package br.org.otus.laboratory.extraction.model;

import java.util.List;

import com.google.gson.GsonBuilder;

public class ParticipantLaboratoryRecordExtraction {

  private List<ParticipantLaboratoryResultExtraction> results;

  public static String serialize(ParticipantLaboratoryRecordExtraction progressionReport) {
    return ParticipantLaboratoryRecordExtraction.getGsonBuilder().create().toJson(progressionReport);
  }

  public static ParticipantLaboratoryRecordExtraction deserialize(String progressionReportJson) {
    GsonBuilder builder = ParticipantLaboratoryRecordExtraction.getGsonBuilder();
    return builder.create().fromJson(progressionReportJson, ParticipantLaboratoryRecordExtraction.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();

    return builder;
  }

  public List<ParticipantLaboratoryResultExtraction> getResults() {
    return results;
  }

}
