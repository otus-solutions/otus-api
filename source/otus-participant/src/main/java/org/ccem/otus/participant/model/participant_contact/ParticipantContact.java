package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.ObjectIdToStringAdapter;

public class ParticipantContact {

  private ObjectId _id;
  private String objectType;
  private Long recruitmentNumber;
  private ParticipantContactItem mainEmail;
  private ParticipantContactItem mainAddress;
  private ParticipantContactItem mainPhoneNumber;
  private ParticipantContactItem[] otherEmails;
  private ParticipantContactItem[] otherAddresses;
  private ParticipantContactItem[] otherPhoneNumbers;

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public ParticipantContactItem getMainEmail() {
    return mainEmail;
  }

  public ParticipantContactItem getMainAddress() {
    return mainAddress;
  }

  public ParticipantContactItem getMainPhoneNumber() {
    return mainPhoneNumber;
  }

  public ParticipantContactItem[] getOtherEmails() {
    return otherEmails;
  }

  public ParticipantContactItem[] getOtherAddresses() {
    return otherAddresses;
  }

  public ParticipantContactItem[] getOtherPhoneNumbers() {
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
