package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;

public class ParticipantContactStringValue implements ParticipantContactItemValue {

  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public static String serialize(ParticipantContactStringValue participantContactStringValue){
    return (new GsonBuilder()).create().toJson(participantContactStringValue);
  }

  public static ParticipantContactStringValue deserialize(String participantContactItemStringValueJson){
    return (new GsonBuilder()).create().fromJson(participantContactItemStringValueJson, ParticipantContactStringValue.class);
  }

  @Override
  public Boolean isValid() {
    return (getValue() != null && getValue().length() > 0);
  }
}
