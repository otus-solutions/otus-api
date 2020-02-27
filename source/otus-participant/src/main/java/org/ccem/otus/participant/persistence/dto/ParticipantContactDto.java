package org.ccem.otus.participant.persistence.dto;

import com.google.gson.GsonBuilder;

public class ParticipantContactDto {

  private Long recruitmentNumber;
  private String type;
  private String newValue;
  private String valueSwapWith;
  private int indexAtContactArray;

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getType() {
    return type;
  }

  public String getNewValue() {
    return newValue;
  }

  public String getValueSwapWith() {
    return valueSwapWith;
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
