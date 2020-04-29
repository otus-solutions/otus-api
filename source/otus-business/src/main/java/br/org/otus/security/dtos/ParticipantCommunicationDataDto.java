package br.org.otus.security.dtos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParticipantCommunicationDataDto {
  private String _id;
  private String email;
  private LinkedHashMap<String, String> variables;

  public ParticipantCommunicationDataDto() {
    variables = new LinkedHashMap<String, String>();
    variables.put("token", "");
    variables.put("host", "");
  }

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public static ParticipantCommunicationDataDto deserialize(String json) {
    ParticipantCommunicationDataDto emailNotification = ParticipantCommunicationDataDto.getGsonBuilder().create().fromJson(json, ParticipantCommunicationDataDto.class);
    return emailNotification;
  }

  public static String serialize(ParticipantCommunicationDataDto communicationData) {
    return ParticipantCommunicationDataDto.getGsonBuilder().create().toJson(communicationData);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }

  public void setId(String id) {
    this._id = id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void pushVariable(String key, String value) {
    if (this.variables.containsKey(key)) {
      this.variables.replace(key, "", value);
    }
  }
}
