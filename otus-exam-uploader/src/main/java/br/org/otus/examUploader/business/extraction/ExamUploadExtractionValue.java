package br.org.otus.examUploader.business.extraction;

import org.ccem.otus.utils.LongAdapter;

import com.google.gson.GsonBuilder;

public class ExamUploadExtractionValue {

  private Long recruitmentNumber;
  // private LinkedList<> results;

  public static String serialize(ExamUploadExtractionValue progressionReport) {
    return ExamUploadExtractionValue.getGsonBuilder().create().toJson(progressionReport);
  }

  public static ExamUploadExtractionValue deserialize(String progressionReportJson) {
    GsonBuilder builder = ExamUploadExtractionValue.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(progressionReportJson, ExamUploadExtractionValue.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();

    return builder;
  }

}
