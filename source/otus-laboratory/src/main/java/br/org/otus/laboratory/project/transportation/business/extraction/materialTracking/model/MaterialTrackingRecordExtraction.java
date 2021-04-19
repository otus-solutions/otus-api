package br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MaterialTrackingRecordExtraction implements Comparable<MaterialTrackingRecordExtraction> {

  private List<MaterialTrackingResultExtraction> results;

  public static String serialize(MaterialTrackingRecordExtraction progressionReport) {
    return MaterialTrackingRecordExtraction.getGsonBuilder().create().toJson(progressionReport);
  }

  public static MaterialTrackingRecordExtraction deserialize(String progressionReportJson) {
    GsonBuilder builder = MaterialTrackingRecordExtraction.getGsonBuilder();
    return builder.create().fromJson(progressionReportJson, MaterialTrackingRecordExtraction.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    builder.serializeNulls();
    return builder;
  }

  public List<MaterialTrackingResultExtraction> getResults() {
    return results;
  }

  @Override
  public int compareTo(@NotNull MaterialTrackingRecordExtraction materialTrackingRecordExtraction) {
    return 0;
  }
}
