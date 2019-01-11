package br.org.otus.examUploader.business.extraction.enums;

public enum ExamUploadExtractionHeaders {

  RECRUITMENT_NUMBER("recruitment_number");

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
