package br.org.otus.examUploader;

import com.google.gson.GsonBuilder;

public class ExtraVariable {
  private String name;
  private String value;

  public ExtraVariable(String name, String value) {
    this.name = name;
    this.value = value;
  };

  public String getName() { return name; }
  public String getValue() { return value; }

  public static String serialize(ExtraVariable extraVariable) {
    return getGsonBuilder().create().toJson(extraVariable);
  }

  public static ExtraVariable deserialize(String extraVariableJson) {
    return ExtraVariable.getGsonBuilder().create().fromJson(extraVariableJson, ExtraVariable.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }
}
