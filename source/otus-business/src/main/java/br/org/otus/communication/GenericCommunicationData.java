package br.org.otus.communication;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.LinkedHashMap;

public class GenericCommunicationData {
  private ObjectId _id = new ObjectId("5fa61989bbc9508293069558");
  private String email;
  private LinkedHashMap<String, String> variables;

  public GenericCommunicationData(String id) {
    _id = new ObjectId(id);
    variables = new LinkedHashMap<>();
  }

  public String toJson() {
    return serialize(this);
  }

  public static GenericCommunicationData deserialize(String json) {
    GenericCommunicationData emailNotification = GenericCommunicationData.getGsonBuilder().create().fromJson(json, GenericCommunicationData.class);
    return emailNotification;
  }

  public static String serialize(GenericCommunicationData followUpCommunicationData) {
    return GenericCommunicationData.getGsonBuilder().create().toJson(followUpCommunicationData);
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

  public void set_id(ObjectId _id) {
    this._id = _id;
  }

  public void pushVariable(String key, String value) {
    this.variables.put(key, value);
  }
}
