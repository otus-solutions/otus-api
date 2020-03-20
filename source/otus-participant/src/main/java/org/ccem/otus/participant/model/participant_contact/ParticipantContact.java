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
  private ParticipantContactItemSet<Email> email;
  private ParticipantContactItemSet<Address> address;
  private ParticipantContactItemSet<PhoneNumber> phoneNumber;

  public ObjectId getObjectId() {
    return _id;
  }

  public String getObjectType() {
    return objectType;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public ParticipantContactItemSet<Email> getEmail() {
    return email;
  }

  public ParticipantContactItemSet<Address> getAddress() {
    return address;
  }

  public ParticipantContactItemSet<PhoneNumber> getPhoneNumber() {
    return phoneNumber;
  }

  public ParticipantContactItemSet getParticipantContactItemSetByType(ParticipantContactTypeOptions participantContactType){
    HashMap<ParticipantContactTypeOptions, ParticipantContactItemSet> map = new HashMap<>();
    map.put(ParticipantContactTypeOptions.EMAIL, getEmail());
    map.put(ParticipantContactTypeOptions.ADDRESS, getAddress());
    map.put(ParticipantContactTypeOptions.PHONE, getPhoneNumber());
    return map.get(participantContactType);
  }

  public ParticipantContactItemSet getParticipantContactItemSetByType(String participantContactType){
    //TODO check usefull
    HashMap<String, ParticipantContactItemSet> map = new HashMap<>();
    map.put(ParticipantContactTypeOptions.EMAIL.getName(), getEmail());
    map.put(ParticipantContactTypeOptions.ADDRESS.getName(), getAddress());
    map.put(ParticipantContactTypeOptions.PHONE.getName(), getPhoneNumber());
    return map.get(participantContactType);
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
