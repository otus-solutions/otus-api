package org.ccem.otus.participant.model.participant_contact;

public class Email extends ParticipantContactItemValueString {

  public static String serialize(Email email){
    return getGsonBuilder().create().toJson(email);
  }

  public static Email deserialize(String emailJson){
    return getGsonBuilder().create().fromJson(emailJson, Email.class);
  }

  @Override
  public String toJson() {
    return Email.serialize(this);
  }
}
