package org.ccem.otus.model.dataSources.dcm.retinography;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class DCMRetinographyDataSourceResult {

  private String id;
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

  public static List<DCMRetinographyDataSourceResult> deserializeList(String json) {
    ArrayList<DCMRetinographyDataSourceResult> results = new ArrayList<>();
    JsonParser parser = new JsonParser();
    JsonArray asJsonArray = parser.parse(json).getAsJsonArray();
    for (JsonElement jsonElement : asJsonArray) {
      results.add(DCMRetinographyDataSourceResult.getGsonBuilder().create().fromJson(jsonElement, DCMRetinographyDataSourceResult.class));
    }
    return results;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    return builder;
  }

}
