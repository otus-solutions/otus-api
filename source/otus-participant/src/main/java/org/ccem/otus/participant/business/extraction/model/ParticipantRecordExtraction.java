package org.ccem.otus.participant.business.extraction.model;

import java.util.List;

import com.google.gson.GsonBuilder;
import org.ccem.otus.participant.utils.LongAdapter;

public class ParticipantRecordExtraction {

  private Long recruitmentNumber;
  private List<ParticipantResultExtraction> results;

  public static String serialize(ParticipantRecordExtraction progressionReport) {
    return ParticipantRecordExtraction.getGsonBuilder().create().toJson(progressionReport);
  }

  public static ParticipantRecordExtraction deserialize(String progressionReportJson) {
    GsonBuilder builder = ParticipantRecordExtraction.getGsonBuilder();
    return builder.create().fromJson(progressionReportJson, ParticipantRecordExtraction.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    builder.serializeNulls();

    return builder;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public List<ParticipantResultExtraction> getResults() {
    return results;
  }

}
