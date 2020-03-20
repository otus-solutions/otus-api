package org.ccem.otus.participant.model.participant_contact;

public class PhoneNumber extends ParticipantContactItemValueString {

  public static String serialize(PhoneNumber participantContactItems){
    return getGsonBuilder().create().toJson(participantContactItems);
  }

  public static PhoneNumber deserialize(String participantContactItemsJson){
    return getGsonBuilder().create().fromJson(participantContactItemsJson, PhoneNumber.class);
  }

  @Override
  public String toJson() {
    return PhoneNumber.serialize(this);
  }
}
