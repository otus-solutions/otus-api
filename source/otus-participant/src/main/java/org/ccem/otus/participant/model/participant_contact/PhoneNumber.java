package org.ccem.otus.participant.model.participant_contact;

public class PhoneNumber extends ParticipantContactItemValueString {

  public static String serialize(PhoneNumber phoneNumber){
    return getGsonBuilder().create().toJson(phoneNumber);
  }

  public static PhoneNumber deserialize(String phoneNumberJson){
    return getGsonBuilder().create().fromJson(phoneNumberJson, PhoneNumber.class);
  }

  @Override
  public String toJson() {
    return PhoneNumber.serialize(this);
  }
}
