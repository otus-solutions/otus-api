package org.ccem.otus.participant.model.participant_contact;

import com.google.gson.internal.LinkedTreeMap;

public class Address extends ParticipantContactItemValue {

  private String postalCode;
  private String street;
  private Integer streetNumber;
  private String complements;
  private String neighbourhood;
  private String city;
  private String state;
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

  public String getState() { return state; }

  public String getCountry() {
    return country;
  }

  public static String serialize(Address participantContactItemAddress){
    return getGsonBuilder().create().toJson(participantContactItemAddress);
  }

  public static Address deserialize(String participantContactItemAddressJson){
    return getGsonBuilder().create().fromJson(participantContactItemAddressJson, Address.class);
  }

  @Override
  public boolean isValid() {
    return (getStreet()!=null && getStreetNumber()!=null &&
      getNeighbourhood()!=null && getCity()!=null && getState()!=null && getCountry()!=null);
  }

  @Override
  public void setFromLinkedTreeMap(LinkedTreeMap map) {
    postalCode = (String)map.get("postalCode");
    street = (String)map.get("street");
    streetNumber = ((Double) map.get("streetNumber")).intValue();
    complements = (String)map.get("complements");
    neighbourhood = (String)map.get("neighbourhood");
    city = (String)map.get("city");
    state = (String)map.get("state");
    country = (String)map.get("country");
  }

  @Override
  public String toJson() {
    return Address.serialize(this);
  }
}
