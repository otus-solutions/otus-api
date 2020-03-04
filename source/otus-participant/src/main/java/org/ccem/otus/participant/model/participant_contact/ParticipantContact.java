package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.HashMap;

public class ParticipantContact {

  private ObjectId _id;
  private String objectType;
  private Long recruitmentNumber;
  private ParticipantContactItem mainEmail;
  private ParticipantContactItem mainAddress;
  private ParticipantContactItem mainPhoneNumber;
  private ParticipantContactItem[] secondaryEmails;
  private ParticipantContactItem[] secondaryAddresses;
  private ParticipantContactItem[] secondaryPhoneNumbers;

  public ObjectId getObjectId() {
    return _id;
  }

  public String getObjectType() {
    return objectType;
  }

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

  public ParticipantContactItem[] getSecondaryEmails() {
    return secondaryEmails;
  }

  public ParticipantContactItem[] getSecondaryAddresses() {
    return secondaryAddresses;
  }

  public ParticipantContactItem[] getSecondaryPhoneNumbers() {
    return secondaryPhoneNumbers;
  }

  public ParticipantContactItem getMainParticipantContactItemByType(String participantContactType){
    return
      (new HashMap<String, ParticipantContactItem>(){
      {
        put(ParticipantContactTypeOptions.EMAIL.getName(), getMainEmail());
        put(ParticipantContactTypeOptions.ADDRESS.getName(), getMainAddress());
        put(ParticipantContactTypeOptions.PHONE.getName(), getMainPhoneNumber());
      }
    }).get(participantContactType);
  }

  public ParticipantContactItem[] getSecondaryParticipantContactsItemByType(String participantContactType){
    return
      (new HashMap<String, ParticipantContactItem[]>(){
      {
        put(ParticipantContactTypeOptions.EMAIL.getName(), getSecondaryEmails());
        put(ParticipantContactTypeOptions.ADDRESS.getName(), getSecondaryAddresses());
        put(ParticipantContactTypeOptions.PHONE.getName(), getSecondaryPhoneNumbers());
      }
    }).get(participantContactType);
  }

  public static String serialize(ParticipantContact participantContact){
    return getGsonBuilder().create().toJson(participantContact);
  }

  public static ParticipantContact deserialize(String participantContactJson){
    return getGsonBuilder().create().fromJson(participantContactJson, ParticipantContact.class);
  }

  public static GsonBuilder getGsonBuilder(){
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    return builder;
  }

  public static GsonBuilder getFrontGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }
}
