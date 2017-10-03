package org.ccem.otus.service.extraction.enums;

public enum SurveyActivityExtractionHeaders {

	RECRUITMENT_NUMBER("recruitment_number"),
	ACRONYM("acronym"),
	CATEGORY("category"),
	TYPE("type"),
	INTERVIEWER("interviewer"),
	CURRENT_STATUS("current_status"),
	CURRENT_STATUS_DATE("current_status_date"),
	CREATION_DATE("creation_date"),
	PAPPER_REALIZATION_DATE("papper_realization_date"),
	LAST_FINALIZATION_DATE("last_finalization_date"),

	QUESTION_COMMENT_SUFFIX("_metadata"),
	QUESTION_METADATA_SUFFIX("_comment");

	private final String name;

	private SurveyActivityExtractionHeaders(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

}

