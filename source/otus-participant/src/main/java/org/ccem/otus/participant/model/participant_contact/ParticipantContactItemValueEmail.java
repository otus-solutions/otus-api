package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;

public class ParticipantContactItemValueEmail extends ParticipantContactItemValueString {

  public ParticipantContactItemValueEmail() {
    objectType = "Email";
  }

  public static String serialize(ParticipantContactItemValueEmail participantContactItems){
    return (new GsonBuilder()).create().toJson(participantContactItems);
  }

  public static ParticipantContactItemValueEmail deserialize(String participantContactItemsJson){
    return (new GsonBuilder()).create().fromJson(participantContactItemsJson, ParticipantContactItemValueEmail.class);
  }

  @Override
  public String toJson() {
    return ParticipantContactItemValueEmail.serialize(this);
  }
}
