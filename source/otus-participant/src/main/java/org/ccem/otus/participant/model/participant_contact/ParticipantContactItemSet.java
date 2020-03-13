package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class ParticipantContactItemSet {

  private ParticipantContactItem main;
  private ParticipantContactItem second;
  private ParticipantContactItem third;
  private ParticipantContactItem fourth;
  private ParticipantContactItem fifth;

  public ParticipantContactItem getMain() {
    return main;
  }

  public ParticipantContactItem getSecond() {
    return second;
  }

  public ParticipantContactItem getThird() {
    return third;
  }

  public ParticipantContactItem getFourth() {
    return fourth;
  }

  public ParticipantContactItem getFifth() {
    return fifth;
  }

  public ParticipantContactItem getNotMainItem(int position){
    return
      (new HashMap<Integer, ParticipantContactItem>(){
        {
          put(2, getSecond());
          put(3, getThird());
          put(4, getFourth());
          put(5, getFifth());
        }
      }).get(position);
  }

  public static String serialize(ParticipantContactItemSet participantContactItems){
    return (new GsonBuilder()).create().toJson(participantContactItems);
  }

  public static ParticipantContactItemSet deserialize(String participantContactItemsJson){
    return (new GsonBuilder()).create().fromJson(participantContactItemsJson, ParticipantContactItemSet.class);
  }
}
