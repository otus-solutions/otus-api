package org.ccem.otus.participant.persistence.dto;

import com.google.gson.GsonBuilder;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactItem;

public class ParticipantContactDto {

  private Long recruitmentNumber;
  private String type;
  private ParticipantContactItem newValue;
  private int indexAtContactArray;

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
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
}
