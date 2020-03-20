package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.internal.LinkedTreeMap;

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

  @Override
  public void setFromLinkedTreeMap(LinkedTreeMap map) {
    content = (String)map.get("content");
  }
}
