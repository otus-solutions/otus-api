package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

public class ParticipantContactItem<T extends ParticipantContactItemValue> {

  private T value;
  private String observation;

  public T getValue() {
    return value;
  }

  public String getObservation() {
    return observation;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public void setFromLinkedTreeMap(LinkedTreeMap map) {
    value.setFromLinkedTreeMap((LinkedTreeMap)map.get("value"));
    observation = (String)map.get("observation");
  }

  public static String serialize(ParticipantContactItem participantContactItem){
    return (new GsonBuilder()).create().toJson(participantContactItem);
  }

  public static ParticipantContactItem deserialize(String participantContactItemJson){
    return (new GsonBuilder()).create().fromJson(participantContactItemJson, ParticipantContactItem.class);
  }

  public boolean isValid(){
    return (getValue() != null && getValue().isValid());
  }
}
