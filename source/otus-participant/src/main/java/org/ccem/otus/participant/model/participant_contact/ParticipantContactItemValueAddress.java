package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.GsonBuilder;

public class ParticipantContactItemValueAddress extends ParticipantContactItemValue {

  private String postalCode;
  private String street;
  private Integer streetNumber;
  private String complements;
  private String neighbourhood;
  private String city;
  private String country;

  public String getPostalCode() {
    return postalCode;
  }

  public String getStreet() {
    return street;
  }

  public Integer getStreetNumber() {
    return streetNumber;
  }

  public String getComplements() {
    return complements;
  }

  public String getNeighbourhood() {
    return neighbourhood;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

//  public static String serialize(ParticipantContactItemAddress participantContactItemAddress){
//    return (new GsonBuilder()).create().toJson(participantContactItemAddress);
//  }
//
//  public static ParticipantContactItemAddress deserialize(String participantContactItemAddressJson){
//    return (new GsonBuilder()).create().fromJson(participantContactItemAddressJson, ParticipantContactItemAddress.class);
//  }

  @Override
  public boolean isValid() {
    return true;
  }
}
