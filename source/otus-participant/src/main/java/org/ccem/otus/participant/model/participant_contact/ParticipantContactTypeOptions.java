package org.ccem.otus.participant.model.participant_contact;

import java.util.Arrays;

public enum ParticipantContactTypeOptions {

  EMAIL("EMAIL"),
  ADDRESS("ADDRESS"),
  PHONE("PHONE");

  private String value;

  ParticipantContactTypeOptions(String value){
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static boolean contains(String otherValue){
    return Arrays.asList(ParticipantContactTypeOptions.values()).stream()
      .map(ParticipantContactTypeOptions::getValue)
      .anyMatch(otherValue::equals);
  }

}
