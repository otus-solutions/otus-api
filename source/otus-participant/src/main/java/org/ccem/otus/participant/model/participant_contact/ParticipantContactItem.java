package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;

public class ParticipantContactItem {

  private String contactValue;
  private String observation;

  public String getContactValue() {
    return contactValue;
  }

  public String getObservation() {
    return observation;
  }

  public static String serialize(ParticipantContactItem participantContactItem){
    return (new GsonBuilder()).create().toJson(participantContactItem);
  }

  public static ParticipantContactItem deserialize(String participantContactItemJson){
    return (new GsonBuilder()).create().fromJson(participantContactItemJson, ParticipantContactItem.class);
  }
}
