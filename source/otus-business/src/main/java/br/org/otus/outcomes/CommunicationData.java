package br.org.otus.outcomes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.variables.StaticVariableRequestDTO;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class CommunicationData {
  private ObjectId _id;
  private String email;
  private LinkedHashMap<String, String> variables;

  private final static ArrayList<String> VARIABLES_LIST =  new ArrayList<String>() {{
    add("participant_name");
    add("event_name");
  }};

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public static CommunicationData deserialize(String json) {
    CommunicationData emailNotification = CommunicationData.getGsonBuilder().create().fromJson(json, CommunicationData.class);
    return emailNotification;
  }

  public static String serialize(CommunicationData communicationData) {
    return CommunicationData.getGsonBuilder().create().toJson(communicationData);
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
    if (this.variables.containsKey(key)) {
      this.variables.replace(key, "", value);
    }
  }
}
