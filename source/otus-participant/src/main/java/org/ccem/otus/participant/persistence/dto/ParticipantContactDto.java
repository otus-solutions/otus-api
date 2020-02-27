package org.ccem.otus.participant.persistence.dto;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;

public class ParticipantContactDto implements Dto {

  private String objectIdStr;
  private String type;
  private ParticipantContactItem newValue;
  private int indexAtContactArray;

  public String getObjectIdStr() {
    return objectIdStr;
  }

  public String getType() {
    return type;
  }

  public ParticipantContactItem getNewValue() {
    return newValue;
  }

  public int getIndexAtContactArray() {
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
      new ObjectId(getObjectIdStr());
      return ParticipantContactTypeOptions.contains(getType()) &&
        newValue.isValid() &&
        (indexAtContactArray >= 0);
    }
    catch (Exception e){
      return false;
    }
  }
}
