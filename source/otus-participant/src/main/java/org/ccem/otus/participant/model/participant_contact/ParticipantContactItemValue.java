package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

public abstract class ParticipantContactItemValue {

  protected static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

  protected static GsonBuilder getFrontGsonBuilder() {
    return new GsonBuilder();
  }

  public abstract boolean isValid();

  public abstract void setFromLinkedTreeMap(LinkedTreeMap map);

  public abstract String toJson();

}
