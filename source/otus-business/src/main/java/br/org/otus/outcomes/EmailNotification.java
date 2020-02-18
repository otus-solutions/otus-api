package br.org.otus.outcomes;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.ArrayList;

public class EmailNotification {
  private ObjectId _id;
  private ArrayList<String> variables;


  public static EmailNotification deserialize(String json) {
    EmailNotification emailNotification = EmailNotification.getGsonBuilder().create().fromJson(json, EmailNotification.class);
    return emailNotification;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }

  public ArrayList<String> getVariables() {
    return variables;
  }

  public void setVariables(ArrayList<String> variables) {
    this.variables = variables;
  }
}
