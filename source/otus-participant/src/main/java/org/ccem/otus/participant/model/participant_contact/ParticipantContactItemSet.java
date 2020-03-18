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

  public ParticipantContactItem<T> getItemByPosition(ParticipantContactPositionOptions position){
    HashMap<ParticipantContactPositionOptions, ParticipantContactItem<T>> map = new HashMap<>();
    map.put(ParticipantContactPositionOptions.MAIN, getMain());
    map.put(ParticipantContactPositionOptions.SECOND, getSecond());
    map.put(ParticipantContactPositionOptions.THIRD, getThird());
    map.put(ParticipantContactPositionOptions.FOURTH, getFourth());
    map.put(ParticipantContactPositionOptions.FIFTH, getFifth());
    return map.get(position);
  }

  public ParticipantContactItem<T> getNotMainItem(String position){
    //TODO check usefull
    HashMap<ParticipantContactPositionOptions, ParticipantContactItem<T>> map = new HashMap<>();
    map.put(ParticipantContactPositionOptions.SECOND, getSecond());
    map.put(ParticipantContactPositionOptions.THIRD, getThird());
    map.put(ParticipantContactPositionOptions.FOURTH, getFourth());
    map.put(ParticipantContactPositionOptions.FIFTH, getFifth());
    return map.get(position);
  }

  public static String serialize(ParticipantContactItemSet participantContactItems){
    return (new GsonBuilder()).create().toJson(participantContactItems);
  }

  public static ParticipantContactItemSet deserialize(String participantContactItemsJson){
    return (new GsonBuilder()).create().fromJson(participantContactItemsJson, ParticipantContactItemSet.class);
  }
}
