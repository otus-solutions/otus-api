package br.org.otus.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ActivitySendingCommunicationData {
  private ObjectId _id = new ObjectId("5ea88862ae51d800083aeba7");
  private String email;
  private LinkedHashMap<String, String> variables;

  public ActivitySendingCommunicationData() {
    variables = new LinkedHashMap<>();
  }

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public static ActivitySendingCommunicationData deserialize(String json) {
    ActivitySendingCommunicationData emailNotification = ActivitySendingCommunicationData.getGsonBuilder().create().fromJson(json, ActivitySendingCommunicationData.class);
    return emailNotification;
  }

  public static String serialize(ActivitySendingCommunicationData followUpCommunicationData) {
    return ActivitySendingCommunicationData.getGsonBuilder().create().toJson(followUpCommunicationData);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void pushVariable(String key, String value) {
    this.variables.put(key, value);
  }
}
