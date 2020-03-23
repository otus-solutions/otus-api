package org.ccem.otus.participant.model.participant_contact;

import java.util.Arrays;

public enum ParticipantContactTypeOptions {

  EMAIL("email"),
  ADDRESS("address"),
  PHONE("phoneNumber");

  private String name;

  ParticipantContactTypeOptions(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static boolean contains(String otherValue){
    return Arrays.asList(ParticipantContactTypeOptions.values()).stream()
      .map(ParticipantContactTypeOptions::getName)
      .anyMatch(otherValue::equals);
  }

  public static ParticipantContactTypeOptions fromString(String value){
    for (ParticipantContactTypeOptions option : ParticipantContactTypeOptions.values()) {
      if(option.getName().equals(value)){
        return option;
      }
    }
    throw new IllegalArgumentException("String value " + value + " is not a ParticipantContactTypeOptions valid enum");
  }

}
