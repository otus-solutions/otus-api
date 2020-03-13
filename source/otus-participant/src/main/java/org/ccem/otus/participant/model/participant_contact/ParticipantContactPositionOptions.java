package org.ccem.otus.participant.model.participant_contact;

import java.util.Arrays;

public enum ParticipantContactPositionOptions {

  MAIN("EMAIL"),
  SECOND("ADDRESS"),
  THIRD("PHONE"),
  FOURTH("FOURTH"),
  FIFTH("FIFTH");

  private String name;

  ParticipantContactPositionOptions(String name){
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
