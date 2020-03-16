package org.ccem.otus.participant.model.participant_contact;

public class ParticipantContactItemValueString extends ParticipantContactItemValue {

  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

//  public static String serialize(ParticipantContactItemStringValue participantContactStringValue){
//    return (new GsonBuilder()).create().toJson(participantContactStringValue);
//  }
//
//  public static ParticipantContactItemStringValue deserialize(String participantContactItemStringValueJson){
//    return (new GsonBuilder()).create().fromJson(participantContactItemStringValueJson, ParticipantContactItemStringValue.class);
//  }

  @Override
  public boolean isValid() {
    return true;
  }
}
