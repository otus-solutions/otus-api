package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.HashMap;

public class ParticipantContact {

  private ObjectId _id;
  private String objectType;
  private Long recruitmentNumber;
  @SerializedName("email")
  private ParticipantContactItemSet<Email> emailSet;
  @SerializedName("address")
  private ParticipantContactItemSet<Address> addressSet;
  @SerializedName("phoneNumber")
  private ParticipantContactItemSet<PhoneNumber> phoneNumberSet;

  public ObjectId getObjectId() {
    return _id;
  }

  public String getObjectType() {
    return objectType;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public ParticipantContactItemSet<Email> getEmailSet() {
    return emailSet;
  }

  public ParticipantContactItemSet<Address> getAddressSet() {
    return addressSet;
  }

  public ParticipantContactItemSet<PhoneNumber> getPhoneNumberSet() {
    return phoneNumberSet;
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

  public ParticipantContactItemSet getParticipantContactItemSetByType(String participantContactType){
    HashMap<String, ParticipantContactItemSet> map = new HashMap<>();
    map.put(ParticipantContactTypeOptions.EMAIL.getName(), getEmailSet());
    map.put(ParticipantContactTypeOptions.ADDRESS.getName(), getAddressSet());
    map.put(ParticipantContactTypeOptions.PHONE.getName(), getPhoneNumberSet());
    return map.get(participantContactType);
  }

  public boolean hasAllMainContacts(){
    return itemSetExistAndHasMainContact(getEmailSet()) &&
      itemSetExistAndHasMainContact(getAddressSet()) &&
      itemSetExistAndHasMainContact(getPhoneNumberSet());
  }
  private boolean itemSetExistAndHasMainContact(ParticipantContactItemSet itemSet){
    try{
      return (itemSet.getMain() != null);
    }
    catch (NullPointerException e){
      return false;
    }
  }
}
