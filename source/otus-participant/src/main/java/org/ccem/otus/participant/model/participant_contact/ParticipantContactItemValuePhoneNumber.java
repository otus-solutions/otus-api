package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;

public class ParticipantContactItemValuePhoneNumber extends ParticipantContactItemValueString {

  public ParticipantContactItemValuePhoneNumber() {
    objectType = "PhoneNumber";
  }

  public static String serialize(ParticipantContactItemValuePhoneNumber participantContactItems){
    return (new GsonBuilder()).create().toJson(participantContactItems);
  }

  public static ParticipantContactItemValuePhoneNumber deserialize(String participantContactItemsJson){
    return (new GsonBuilder()).create().fromJson(participantContactItemsJson, ParticipantContactItemValuePhoneNumber.class);
  }

  @Override
  public String toJson() {
    return ParticipantContactItemValuePhoneNumber.serialize(this);
  }
}
