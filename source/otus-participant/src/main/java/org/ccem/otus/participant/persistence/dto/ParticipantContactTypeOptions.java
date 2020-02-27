package org.ccem.otus.participant.persistence.dto;

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

  public static boolean contains(String othetValue){
    for (ParticipantContactTypeOptions option : ParticipantContactTypeOptions.values()) {
      if(othetValue.equals(option.getValue())){
        return true;
      }
    }
    return false;
  }

}
