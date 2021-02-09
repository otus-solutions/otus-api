package br.org.otus.laboratory.extraction.enums;

public enum LaboratoryExtractionHeaders {

  RECRUITMENT_NUMBER("recruitment_number"),
  UNATTACHED_IDENTIFICATION("unattached_identification"),
  TUBE_CODE("tube_code"),
  TUBE_QUALITY_CONTROL("tube_quality_control"),
  TUBE_TYPE("tube_type"),
  TUBE_MOMENT("tube_moment"),
  TUBE_COLLECTION_DATE("tube_collection_date"),
  TUBE_RESPONSIBLE("tube_responsible"),
  ALIQUOT_CODE("aliquot_code"),
  ALIQUOT_NAME("aliquot_name"),
  ALIQUOT_CONTAINER("aliquot_container"),
  ALIQUOT_PROCESSING_DATE("aliquot_processing_date"),
  ALIQUOT_REGISTER_DATE("aliquot_register_date"),
  ALIQUOT_RESPONSIBLE("aliquot_responsible"),
  ALIQUOT_ROLE("aliquot_role");

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
