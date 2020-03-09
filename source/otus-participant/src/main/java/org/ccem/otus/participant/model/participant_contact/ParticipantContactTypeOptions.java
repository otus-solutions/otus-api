package org.ccem.otus.participant.model.participant_contact;

import java.util.Arrays;

public enum ParticipantContactTypeOptions {

  EMAIL("EMAIL"),
  ADDRESS("ADDRESS"),
  PHONE("PHONE");

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

}
