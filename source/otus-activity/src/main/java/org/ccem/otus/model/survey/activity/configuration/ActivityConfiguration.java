package org.ccem.otus.model.survey.activity.configuration;

import com.google.gson.GsonBuilder;

public class ActivityConfiguration {

  public static String extractNamingSuffix(String namingPrefix, String lastInsertedName) {
    int lastInsertion = Integer.parseInt(lastInsertedName.substring(namingPrefix.length()));

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(namingPrefix);
    stringBuilder.append(lastInsertion + 1);

    return stringBuilder.toString();
  }

  public static String serialize(ActivityConfiguration surveyActivity) {
    return getGsonBuilder().create().toJson(surveyActivity);
  }

  public static ActivityConfiguration deserialize(String surveyActivity) {
    return getGsonBuilder().create().fromJson(surveyActivity, ActivityConfiguration.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    return gsonBuilder;
  }
}
