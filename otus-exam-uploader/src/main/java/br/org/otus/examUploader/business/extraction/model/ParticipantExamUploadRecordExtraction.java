package br.org.otus.examUploader.business.extraction.model;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.gson.GsonBuilder;

import br.org.otus.examUploader.Observation;

public class ParticipantExamUploadRecordExtraction {

  private ObjectId _id;
  private Long recruitmentNumber;
  private String aliquotCode;
  private String resultName;
  private String value;
  private String releaseDate;
  private List<Observation> observations;

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

  public String getAliquotCode() {
    return aliquotCode;
  }

  public String getResultName() {
    return resultName;
  }

  public String getValue() {
    return value;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public List<Observation> getObservations() {
    return observations;
  }

}
