package org.ccem.otus.participant.model.participant_contact;

public class Email extends ParticipantContactItemValueString {

  public static String serialize(Email participantContactItems){
    return getGsonBuilder().create().toJson(participantContactItems);
  }

  public static Email deserialize(String participantContactItemsJson){
    return getGsonBuilder().create().fromJson(participantContactItemsJson, Email.class);
  }

  @Override
  public String toJson() {
    return Email.serialize(this);
  }
}
