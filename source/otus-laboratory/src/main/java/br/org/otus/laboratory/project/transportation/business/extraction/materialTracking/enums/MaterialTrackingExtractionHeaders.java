package br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.enums;

public enum MaterialTrackingExtractionHeaders {

  MATERIAL_CODE("material_code"),
  ORIGIN("origin"),
  DESTINATION("destination"),
  RECEIPTED("receipted"),
  RECEIVE_RESPONSIBLE("receive_responsible"),
  SENDING_DATE("sending_date"),
  RECEIPT_DATE("receipt_date"),
  OTHER_METADATA("other_metadata"),
  LOT_ID("lot_code");

  private final String value;

  public String getValue() {
    return value;
  }

  private MaterialTrackingExtractionHeaders(String s) {
    value = s;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }

  public String toString() {
    return this.value;
  }
}
