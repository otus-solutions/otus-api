package org.ccem.otus.participant.model.participant_contact;

public abstract class ParticipantContactItemValue {

  protected String objectType;

  public String getObjectType() {
    return objectType;
  }

  public abstract boolean isValid();

  public abstract String toJson();

}
