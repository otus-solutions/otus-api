package org.ccem.otus.participant.model;

import com.google.gson.GsonBuilder;

public class ParticipantContact {

  private String mainEmail;
  private String mainAddress;
  private String mainPhoneNumber;
  private String[] otherEmails;
  private String[] otherAddresses;
  private String[] otherPhoneNumbers;

  public String getMainEmail() {
    return mainEmail;
  }

  public String getMainAddress() {
    return mainAddress;
  }

  public String getMainPhoneNumber() {
    return mainPhoneNumber;
  }

  public String[] getOtherEmails() {
    return otherEmails;
  }

  public String[] getOtherAddresses() {
    return otherAddresses;
  }

  public String[] getOtherPhoneNumbers() {
    return otherPhoneNumbers;
  }

  public boolean hasAllMainValues(){
    return (mainEmail!=null && mainAddress!=null && mainPhoneNumber!=null);
  }

  public static String serialize(ParticipantContact participantContact){
    return getGsonBuilder().create().toJson(participantContact);
  }

  public static ParticipantContact deserialize(String participantContactJson){
    return getGsonBuilder().create().fromJson(participantContactJson, ParticipantContact.class);
  }

  public static GsonBuilder getGsonBuilder(){
    return new GsonBuilder();
  }

}
