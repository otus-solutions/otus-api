package br.org.otus.outcomes;

import com.google.gson.GsonBuilder;

public class Variables {
  private String participant_name;
  private String event_name;

  public static Variables deserialize(String json) {
    Variables variables = Variables.getGsonBuilder().create().fromJson(json, Variables.class);
    return variables;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }

  public String getParticipant_name() {
    return participant_name;
  }

  public void setParticipant_name(String participant_name) {
    this.participant_name = participant_name;
  }

  public String getEvent_name() {
    return event_name;
  }

  public void setEvent_name(String event_name) {
    this.event_name = event_name;
  }
}
