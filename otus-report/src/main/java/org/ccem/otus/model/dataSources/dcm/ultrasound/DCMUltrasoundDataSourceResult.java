package org.ccem.otus.model.dataSources.dcm.ultrasound;

import java.time.LocalDateTime;

import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import com.google.gson.GsonBuilder;

public class DCMUltrasoundDataSourceResult {

  private LocalDateTime date;
  private String result;

  public static String serialize(DCMUltrasoundDataSourceResult result) {
    return DCMUltrasoundDataSourceResult.getGsonBuilder().create().toJson(result);
  }

  public static DCMUltrasoundDataSourceResult deserialize(String json) {
    DCMUltrasoundDataSourceResult result = DCMUltrasoundDataSourceResult.getGsonBuilder().create().fromJson(json,
        DCMUltrasoundDataSourceResult.class);
    return result;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    return builder;
  }

}
