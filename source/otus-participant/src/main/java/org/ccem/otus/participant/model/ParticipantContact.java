package org.ccem.otus.participant.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.ObjectIdToStringAdapter;

public class ParticipantContact {

  private Long recruitmentNumber;
  private String mainEmail;
  private String mainAddress;
  private String mainPhoneNumber;
  private String[] otherEmails;
  private String[] otherAddresses;
  private String[] otherPhoneNumbers;

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

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

  public static GsonBuilder getFrontGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }

}
