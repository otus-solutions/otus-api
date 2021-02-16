package br.org.otus.examUploader.business.extraction.enums;

public enum ExamUploadExtractionHeaders {

  RECRUITMENT_NUMBER("recruitment_number"),
  ALIQUOT_CODE("aliquot_code"),
  EXAM_NAME("exam_name"),
  RESULT_NAME("result_name"),
  RESULT("result"),
  RELEASE_DATE("release_date"),
  OBSERVATIONS("observations"),
  CUTOFFVALUE("cutoff_value"),
  EXTRAVARIABLES("extra_variables");

  private final String value;

  public String getValue() {
    return value;
  }

  private ExamUploadExtractionHeaders(String s) {
    value = s;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }

  public String toString() {
    return this.value;
  }
}
