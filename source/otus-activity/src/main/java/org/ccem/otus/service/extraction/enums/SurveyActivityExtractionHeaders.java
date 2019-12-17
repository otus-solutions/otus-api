package org.ccem.otus.service.extraction.enums;

public enum SurveyActivityExtractionHeaders {
  RECRUITMENT_NUMBER("recruitment_number"),
  PARTICIPANT_FIELD_CENTER("participant_field_center"),
  ACRONYM("acronym"),
  MODE("mode"),
  CATEGORY("category"),
  PARTICIPANT_FIELD_CENTER_BY_ACTIVITY("participant_field_center_by_activity"),
  TYPE("type"),
  INTERVIEWER("interviewer"),
  CURRENT_STATUS("current_status"),
  CURRENT_STATUS_DATE("current_status_date"),
  CREATION_DATE("creation_date"),
  PAPER_REALIZATION_DATE("paper_realization_date"),
  PAPER_INTERVIEWER("paper_interviewer"),
  LAST_FINALIZATION_DATE("last_finalization_date"),
  
  QUESTION_COMMENT_SUFFIX("_comment"),
  QUESTION_METADATA_SUFFIX("_metadata");

  private final String value;

  public String getValue() {
    return value;
  }

  private SurveyActivityExtractionHeaders(String s) {
    value = s;
  }

  public boolean equalsName(String otherValue) {
    return value.equals(otherValue);
  }

  public String toString() {
    return this.value;
  }

}