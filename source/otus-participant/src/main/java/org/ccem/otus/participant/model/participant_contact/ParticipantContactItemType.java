package org.ccem.otus.participant.model.participant_contact;

public enum ParticipantContactItemType {

  EMAIL("email"),
  PHONE("phone"),
  ADDRESS("address");

  private String value;

  ParticipantContactItemType(String value){
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }
}
