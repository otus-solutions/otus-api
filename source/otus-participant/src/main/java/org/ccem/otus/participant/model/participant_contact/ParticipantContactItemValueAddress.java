package org.ccem.otus.participant.model.participant_contact;

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

  public static String serialize(ParticipantContactItemValueAddress participantContactItemAddress){
    return getGsonBuilder().create().toJson(participantContactItemAddress);
  }

  public static ParticipantContactItemValueAddress deserialize(String participantContactItemAddressJson){
    return getGsonBuilder().create().fromJson(participantContactItemAddressJson, ParticipantContactItemValueAddress.class);
  }

  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public String toJson() {
    return ParticipantContactItemValueAddress.serialize(this);
  }
}
