package br.org.otus.laboratory.extraction.enums;

public enum LaboratoryExtractionHeaders {

  RECRUITMENT_NUMBER("recruitment_number"),
  TUBE_CODE("tube_code"),
  QUALITY_CONTROL("quality_control"),
  ALIQUOT_CODE("aliquot_code");

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
