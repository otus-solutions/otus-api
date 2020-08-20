package org.ccem.otus.service.extraction.enums;

public enum ActivityProgressExtractionHeaders {

  CENTER("center"),
  RECRUITMENT_NUMBER("recruitment_number"),
  ACRONYM("acronym"),
  STATUS("status"),
  STATUS_DATE("status_date"),
  INAPPLICABILITY_OBSERVATION("inapplicability_observation"),
  EXTERNAL_ID("external_id");

  private final String value;

  public String getValue() {
    return value;
  }

  private ActivityProgressExtractionHeaders(String s) {
    value = s;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }

  public String toString() {
    return this.value;
  }

}