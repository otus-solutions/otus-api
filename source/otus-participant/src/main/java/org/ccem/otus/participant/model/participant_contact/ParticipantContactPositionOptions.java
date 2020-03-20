package org.ccem.otus.participant.model.participant_contact;

import java.util.Arrays;

public enum ParticipantContactPositionOptions {

  MAIN("main", 1),
  SECOND("second", 2),
  THIRD("third", 3),
  FOURTH("fourth", 4),
  FIFTH("fifth", 5);

  private String name;
  private int ranking;

  ParticipantContactPositionOptions(String name, int ranking){
    this.name = name;
    this.ranking = ranking;
  }

  public String getName() {
    return name;
  }

  public int getRanking() {
    return ranking;
  }

  public static boolean contains(String otherValue){
    return Arrays.asList(ParticipantContactPositionOptions.values()).stream()
      .map(ParticipantContactPositionOptions::getName)
      .anyMatch(otherValue::equals);
  }

  public static ParticipantContactPositionOptions fromString(String name){
    //TODO write loop like contains method
    for (ParticipantContactPositionOptions option : ParticipantContactPositionOptions.values()) {
      if(option.getName().equals(name)){
        return option;
      }
    }
    throw new IllegalArgumentException("The name " + name + " is not a ParticipantContactTypeOptions valid enum");
  }

  public static ParticipantContactPositionOptions fromInt(int ranking){
    //TODO write loop like contains method
    for (ParticipantContactPositionOptions option : ParticipantContactPositionOptions.values()) {
      if(option.getRanking() == ranking){
        return option;
      }
    }
    throw new IllegalArgumentException("The value " + ranking + " is not a ParticipantContactTypeOptions valid enum");
  }
}
