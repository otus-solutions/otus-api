package org.ccem.otus.model.survey.activity.variables;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

public class StaticVariableRequest {
  private String name;
  private String value;
  private String sending;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getSending() {
    return sending;
  }

  public void setSending(String sending) {
    this.sending = sending;
  }

  public static String serialize(StaticVariableRequest staticVariableRequest) {
    return getGsonBuilder().create().toJson(staticVariableRequest);
  }

  public static StaticVariableRequest deserialize(String variables) {
    return StaticVariableRequest.getGsonBuilder().create().fromJson(variables, StaticVariableRequest.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());

    return builder;
  }
}
