package org.ccem.otus.model.dataSources.dcm.retinography;

import java.time.LocalDateTime;

import com.google.gson.GsonBuilder;

public class DCMRetinographyDataSourceResult {

  private LocalDateTime date;
  private String eye;
  private byte[] result;

  public static String serialize(DCMRetinographyDataSourceResult result) {
    return DCMRetinographyDataSourceResult.getGsonBuilder().create().toJson(result);
  }

  public static DCMRetinographyDataSourceResult deserialize(String json) {
    DCMRetinographyDataSourceResult result = DCMRetinographyDataSourceResult.getGsonBuilder().create().fromJson(json, DCMRetinographyDataSourceResult.class);
    return result;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.serializeNulls();
    return builder;
  }

}
