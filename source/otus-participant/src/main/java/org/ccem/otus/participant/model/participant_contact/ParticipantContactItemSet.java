package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class ParticipantContactItemSet<T extends ParticipantContactItemValue> {

  private ParticipantContactItem<T> main;
  private ParticipantContactItem<T> second;
  private ParticipantContactItem<T> third;
  private ParticipantContactItem<T> fourth;
  private ParticipantContactItem<T> fifth;

  public ParticipantContactItem<T> getMain() {
    return main;
  }

  public ParticipantContactItem<T> getSecond() {
    return second;
  }

  public ParticipantContactItem<T> getThird() {
    return third;
  }

  public ParticipantContactItem<T> getFourth() {
    return fourth;
  }

  public ParticipantContactItem<T> getFifth() {
    return fifth;
  }

  public ParticipantContactItem<T> getNotMainItem(String position){
    return
      (new HashMap<String, ParticipantContactItem<T>>(){
        {
          put(ParticipantContactPositionOptions.SECOND.getName(), getSecond());
          put(ParticipantContactPositionOptions.THIRD.getName(), getThird());
          put(ParticipantContactPositionOptions.FOURTH.getName(), getFourth());
          put(ParticipantContactPositionOptions.FIFTH.getName(), getFifth());
        }
      }).get(position);
  }

//  public static String serialize(ParticipantContactItemSet participantContactItems){
//    return (new GsonBuilder()).create().toJson(participantContactItems);
//  }
//
//  public static ParticipantContactItemSet deserialize(String participantContactItemsJson){
//    return (new GsonBuilder()).create().fromJson(participantContactItemsJson, ParticipantContactItemSet.class);
//  }
}
