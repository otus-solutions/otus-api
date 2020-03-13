package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class ParticipantContactItem {

  private ParticipantContactItemValue value;
  private String observation;

  public ParticipantContactItemValue getValue() {
    return value;
  }

  public String getObservation() {
    return observation;
  }

  public HashMap<String, Object> getAllMyAttributes(){
    return new HashMap<String, Object>(){
      {
        put("value", getValue());
        put("observation", getObservation());
      }
    };
  }

  public HashMap<String, Object> getContactValueAttribute(){
    return new HashMap<String, Object>(){
      {
        put("value", getValue());
      }
    };
  }

  public static String serialize(ParticipantContactItem participantContactItem){
    return (new GsonBuilder()).create().toJson(participantContactItem);
  }

  public static ParticipantContactItem deserialize(String participantContactItemJson){
    return (new GsonBuilder()).create().fromJson(participantContactItemJson, ParticipantContactItem.class);
  }

  public boolean isValid(){
    return (getValue() != null && getValue().isValid());
  }
}
