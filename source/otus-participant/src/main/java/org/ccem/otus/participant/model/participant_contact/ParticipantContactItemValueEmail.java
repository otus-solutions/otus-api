package org.ccem.otus.participant.model.participant_contact;

public class ParticipantContactItemValueEmail extends ParticipantContactItemValueString {

  public static String serialize(ParticipantContactItemValueEmail participantContactItems){
    return getGsonBuilder().create().toJson(participantContactItems);
  }

  public static ParticipantContactItemValueEmail deserialize(String participantContactItemsJson){
    return getGsonBuilder().create().fromJson(participantContactItemsJson, ParticipantContactItemValueEmail.class);
  }

  @Override
  public String toJson() {
    return ParticipantContactItemValueEmail.serialize(this);
  }
}
