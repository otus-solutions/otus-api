package org.ccem.otus.participant.persistence.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactTypeOptions;

public class ParticipantContactDto implements Dto {

  private String _id;
  private String type;
  private ParticipantContactItem participantContactItem;
  private Integer secondaryContactIndex;

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

  public Integer getSecondaryContactIndex() {
    return secondaryContactIndex;
  }

  public static String serialize(ParticipantContactDto participantContactDto){
    return (new GsonBuilder()).create().toJson(participantContactDto);
  }

  public static ParticipantContactDto deserialize(String participantContactDtoJson){
    return (new GsonBuilder()).create().fromJson(participantContactDtoJson, ParticipantContactDto.class);
  }

  @Override
  public Boolean isValid() {
    return ObjectId.isValid(getIdStr()) &&
      ParticipantContactTypeOptions.contains(getType()) &&
      (participantContactItem==null || participantContactItem.isValid()) &&
      (secondaryContactIndex ==null || secondaryContactIndex >= 0);
  }
}
