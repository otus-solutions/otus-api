package org.ccem.otus.model.dataSources.examResult;

public class ExamResultDataSourceFilters {

	private String examName;
	private String releaseDate;
	private ExamResultDataSourceFieldCenterFilter fieldCenter;
	private ExamResultDataSourceSexFilter sex;

	public String getExamName() {
		return examName;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public ExamResultDataSourceFieldCenterFilter getFieldCenter() {
		return fieldCenter;
	}

	public ExamResultDataSourceSexFilter getSex() {
		return sex;
	}
}
