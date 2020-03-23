package org.ccem.otus.participant.persistence.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.participant.model.participant_contact.*;

public class ParticipantContactDto implements Dto {

  private String _id;
  private String position;
  private Object contactItem;
  private String type;

  public String getIdStr() {
    return _id;
  }

  public ObjectId getObjectId() {
    return new ObjectId(_id);
  }

  public String getPosition() {
    return position;
  }

  public LinkedTreeMap getContactItem() {
    return (LinkedTreeMap)contactItem;
  }

  public String getType() {
    return type;
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
      (getPosition() != null && ParticipantContactPositionOptions.contains(getPosition())) &&
      (getType()==null || ParticipantContactTypeOptions.contains(getType()));
  }
}
