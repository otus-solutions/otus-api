package org.ccem.otus.model.dataSources.dcm.ultrasound;

import java.time.LocalDateTime;

import com.google.gson.GsonBuilder;

public class DCMUltrasoundDataSourceResult {

  private LocalDateTime date;
  private byte[] result;

  public static String serialize(DCMUltrasoundDataSourceResult result) {
    return DCMUltrasoundDataSourceResult.getGsonBuilder().create().toJson(result);
  }

  public static DCMUltrasoundDataSourceResult deserialize(String json) {
    DCMUltrasoundDataSourceResult result = DCMUltrasoundDataSourceResult.getGsonBuilder().create().fromJson(json, DCMUltrasoundDataSourceResult.class);
    return result;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();
    return builder;
  }

}
