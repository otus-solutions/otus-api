package org.ccem.otus.participant.model.participant_contact;

public class ParticipantContactItemValuePhoneNumber extends ParticipantContactItemValueString {

  public static String serialize(ParticipantContactItemValuePhoneNumber participantContactItems){
    return getGsonBuilder().create().toJson(participantContactItems);
  }

  public static ParticipantContactItemValuePhoneNumber deserialize(String participantContactItemsJson){
    return getGsonBuilder().create().fromJson(participantContactItemsJson, ParticipantContactItemValuePhoneNumber.class);
  }

  @Override
  public String toJson() {
    return ParticipantContactItemValuePhoneNumber.serialize(this);
  }
}
