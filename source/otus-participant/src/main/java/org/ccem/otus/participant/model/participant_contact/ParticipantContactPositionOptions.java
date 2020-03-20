package org.ccem.otus.participant.model.participant_contact;

import java.util.Arrays;

public enum ParticipantContactPositionOptions {

  MAIN("main"),
  SECOND("second"),
  THIRD("third"),
  FOURTH("fourth"),
  FIFTH("fifth");

  private String name;

  ParticipantContactPositionOptions(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static boolean contains(String otherValue){
    return Arrays.asList(ParticipantContactPositionOptions.values()).stream()
      .map(ParticipantContactPositionOptions::getName)
      .anyMatch(otherValue::equals);
  }
}
