package org.ccem.otus.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Set;

public class DataSource {

  private String id;
  private String name;
  private JsonArray data;

  public DataSource(String id, String name, JsonArray data) {
    super();
    this.id = id;
    this.name = name;
    this.data = data;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public JsonArray getData() {
    return data;
  }

  public void setData(JsonArray data) {
    this.data = data;
  }

  public Set<DataSourceElement> getDataAsSet() {
    Type type = new TypeToken<Set<DataSourceElement>>() {
    }.getType();

    Set<DataSourceElement> dataSourceElements = new Gson().fromJson(data, type);

    return dataSourceElements;
  }

  public static DataSource deserialize(String dataSource) {
    return getGsonBuilder().create().fromJson(dataSource, DataSource.class);
  }

  public static String serialize(DataSource dataSource) {
    return getGsonBuilder().create().toJson(dataSource);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.disableHtmlEscaping();
    return builder;
  }

}
