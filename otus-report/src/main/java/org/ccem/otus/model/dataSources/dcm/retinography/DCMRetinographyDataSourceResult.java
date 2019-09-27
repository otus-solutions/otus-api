package org.ccem.otus.model.dataSources.dcm.retinography;

import java.time.LocalDateTime;

import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import com.google.gson.GsonBuilder;

public class DCMRetinographyDataSourceResult {

  private LocalDateTime date;
  private String eye;
  private String result;

  public static String serialize(DCMRetinographyDataSourceResult result) {
    return DCMRetinographyDataSourceResult.getGsonBuilder().create().toJson(result);
  }

  public static DCMRetinographyDataSourceResult deserialize(String json) {
    DCMRetinographyDataSourceResult result = DCMRetinographyDataSourceResult.getGsonBuilder().create().fromJson(json, DCMRetinographyDataSourceResult.class);
    return result;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    return builder;
  }

}
