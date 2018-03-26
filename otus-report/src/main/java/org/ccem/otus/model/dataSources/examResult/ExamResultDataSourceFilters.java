package org.ccem.otus.model.dataSources.examResult;

public class ExamResultDataSourceFilters {

	private String examName;
	private String realizationDate;
	private ExamResultDataSourceFieldCenterFilter fieldCenter;

	public String getExamName() {
		return examName;
	}

	public String getRealizationDate() {
		return realizationDate;
	}

	public ExamResultDataSourceFieldCenterFilter getFieldCenter() {
		return fieldCenter;
	}

}