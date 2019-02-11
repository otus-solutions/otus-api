package br.org.otus.laboratory.extraction.enums;

public enum LaboratoryExtractionHeaders {

  RECRUITMENT_NUMBER("recruitment_number"),
  TUBE_CODE("tube_code"),
  QUALITY_CONTROL("quality_control"),
  TUBE_NAME("tube_name"),
  COLLECTION_DATE("collection_date"),
  RESPONSIBLE("responsible"),
  ALIQUOT_CODE("aliquot_code"),
  ALIQUOT_NAME("aliquot_name"),
  PROCESSING_DATE("processing_date"),
  REGISTER_DATE("register_date");

  private final String value;

  public String getValue() {
    return value;
  }

  private LaboratoryExtractionHeaders(String s) {
    value = s;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }

  public String toString() {
    return this.value;
  }
}
