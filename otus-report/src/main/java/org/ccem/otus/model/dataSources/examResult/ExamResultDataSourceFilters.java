package org.ccem.otus.model.dataSources.examResult;

public class ExamResultDataSourceFilters {

	private String examName;
	private String releaseDate;
	private ExamResultDataSourceFieldCenterFilter fieldCenter;

	public String getExamName() {
		return this.examName;
	}

	public String getReleaseDate() {
		return this.releaseDate;
	}

	public ExamResultDataSourceFieldCenterFilter getFieldCenter() {
		return this.fieldCenter;
	}

}