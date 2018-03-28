package org.ccem.otus.model.dataSources.exam;

import java.util.List;

public class ExamResultDataSourceFilters {

	private String examName;
	private String releaseDate;
	private List<String> fieldCenters;

	public String getExamName() {
		return this.examName;
	}

	public String getReleaseDate() {
		return this.releaseDate;
	}

	public List<String> getFieldCenters() {
		return fieldCenters;
	}

}