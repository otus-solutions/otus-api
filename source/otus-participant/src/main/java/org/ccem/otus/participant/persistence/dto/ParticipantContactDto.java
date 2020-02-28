package org.ccem.otus.participant.persistence.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;

public class ParticipantContactDto implements Dto {

  private String _id;
  private String type;

  @SerializedName("contactItem")
  private ParticipantContactItem participantContactItem;

  @SerializedName("contactItemToSwapWithMain")
  private ParticipantContactItem participantContactItemToSwapWithMainItem;

  @SerializedName("index")
  private Integer indexAtContactArray;

  public String getIdStr() {
    return _id;
  }

  public ObjectId getObjectId() {
    return new ObjectId(_id);
  }

  public String getType() {
    return type;
  }

  public ParticipantContactItem getParticipantContactItem() {
    return participantContactItem;
  }

  public ParticipantContactItem getParticipantContactItemToSwapWithMainItem() {
    return participantContactItemToSwapWithMainItem;
  }

  public Integer getIndexAtContactArray() {
    return indexAtContactArray;
  }

  public static String serialize(ParticipantContactDto participantContactDto){
    return (new GsonBuilder()).create().toJson(participantContactDto);
  }

  public static ParticipantContactDto deserialize(String participantContactDtoJson){
    return (new GsonBuilder()).create().fromJson(participantContactDtoJson, ParticipantContactDto.class);
  }

  @Override
  public Boolean isValid() {
    try{
      new ObjectId(getIdStr());
      return ParticipantContactTypeOptions.contains(getType()) &&
        participantContactItem.isValid() &&
        (indexAtContactArray==null || indexAtContactArray >= 0);
    }
    catch (Exception e){
      return false;
    }
  }
}
