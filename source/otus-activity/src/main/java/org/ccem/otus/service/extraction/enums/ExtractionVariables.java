package org.ccem.otus.service.extraction.enums;

public enum ExtractionVariables {
  SKIPPED_ANSWER(".P"),
  NOT_VISITED(""),
  IGNORED(""),
  VISITED("");

  private final String value;

  public String getValue() {
    return value;
  }

  private ExtractionVariables(String s) {
    value = s;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }

  public String toString() {
    return this.value;
  }
}
