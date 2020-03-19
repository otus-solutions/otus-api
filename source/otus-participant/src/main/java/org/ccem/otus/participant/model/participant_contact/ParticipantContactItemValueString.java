package org.ccem.otus.participant.model.participant_contact;

public abstract class ParticipantContactItemValueString extends ParticipantContactItemValue {

  protected String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean isValid() {
    return true;
  }
}
