package org.ccem.otus.model.dataSources.examResult;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;

public class ExamResultDataSource extends ReportDataSource<ExamResultDataSourceResult> {

	private ExamResultDataSourceFilters filters;

	@Override
	public void addResult(ExamResultDataSourceResult result) {
		super.getResult().add(result);
	}

	@Override
	public ArrayList<Document> builtQuery(Long recruitmentNumber) {
		ArrayList<Document> query = new ArrayList<>();
		this.buildMachStage(recruitmentNumber, query);

		return query;
	}

	private void buildMachStage(Long recruitmentNumber, ArrayList<Document> query) {
		Document matchStageFilters = new Document("recruitmentNumber", recruitmentNumber);
		this.appendExamName(matchStageFilters);
		this.appendRealizationDateFilter(matchStageFilters);
		this.appendFieldCenterFilter(matchStageFilters);

		Document matchStage = new Document("$match", matchStageFilters);
		query.add(matchStage);
	}

	private void appendExamName(Document matchStage) {
		if (this.filters.getExamName() != null) {
			matchStage.append("examName", this.filters.getExamName());
		}
	}

	private void appendRealizationDateFilter(Document matchStage) {
		if (this.filters.getRealizationDate() != null) {
			matchStage.append("realizationDate", this.filters.getRealizationDate());
		}
	}

	private void appendFieldCenterFilter(Document matchStage) {
		if (this.filters.getFieldCenter() != null) {
			matchStage.append("fieldCenter.acronym", this.filters.getFieldCenter().getAcronym());
		}
	}

}
