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
  private ParticipantContactItemSet<ParticipantContactItemValueString> emails;
  private ParticipantContactItemSet<ParticipantContactItemValueAddress> addresses;
  private ParticipantContactItemSet<ParticipantContactItemValueString> phoneNumbers;

  public ObjectId getObjectId() {
    return _id;
  }

  public String getObjectType() {
    return objectType;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public ParticipantContactItemSet<ParticipantContactItemValueString> getEmails() {
    return emails;
  }

  public ParticipantContactItemSet<ParticipantContactItemValueAddress> getAddresses() {
    return addresses;
  }

  public ParticipantContactItemSet<ParticipantContactItemValueString> getPhoneNumbers() {
    return phoneNumbers;
  }

  public ParticipantContactItem getMainParticipantContactItemByType(String participantContactType){
    return
      (new HashMap<String, ParticipantContactItem>(){
      {
        put(ParticipantContactTypeOptions.EMAIL.getName(), getEmails().getMain());
        put(ParticipantContactTypeOptions.ADDRESS.getName(), getAddresses().getMain());
        put(ParticipantContactTypeOptions.PHONE.getName(), getPhoneNumbers().getMain());
      }
    }).get(participantContactType);
  }

  public ParticipantContactItemSet getSecondaryParticipantContactsItemByType(String participantContactType){
    return
      (new HashMap<String, ParticipantContactItemSet>(){
      {
        put(ParticipantContactTypeOptions.EMAIL.getName(), getEmails());
        put(ParticipantContactTypeOptions.ADDRESS.getName(), getAddresses());
        put(ParticipantContactTypeOptions.PHONE.getName(), getPhoneNumbers());
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
