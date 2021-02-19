package org.ccem.otus.participant.service.extraction.enums;

public enum ParticipantContactAttemptsExtractionHeaders {

  RECRUITMENT_NUMBER("recruitment_number"),
  USER("user"),
  STATUS("status"),
  DATE("date"),
  ADDRESS_STREET("address_street"),
  ADDRESS_NUMBER("address_number"),
  ADDRESS_ZIP_CODE("address_zip_code"),
  ADDRESS_COMPLEMENT("address_complement"),
  ADDRESS_DISTRICT("address_district"),
  ADDRESS_CITY("address_city"),
  ADDRESS_STATE("address_state"),
  ADDRESS_COUNTRY("address_country"),
  SECTOR_IBGE("sector_ibge");

  private final String value;

  public String getValue() {
    return value;
  }

  private ParticipantContactAttemptsExtractionHeaders(String s) {
    value = s;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }

  public String toString() {
    return this.value;
  }

}