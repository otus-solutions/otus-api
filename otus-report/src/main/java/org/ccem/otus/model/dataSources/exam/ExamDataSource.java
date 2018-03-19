package org.ccem.otus.model.dataSources.exam;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;

public class ExamDataSource extends ReportDataSource<ExamDataSourceResult> {

	private ExamDataSourceFilters filters;

	@Override
	public void addResult(ExamDataSourceResult result) {
		super.getResult().add(result);
	}

	@Override
	public ArrayList<Document> builtQuery(Long recruitmentNumber) {
		ArrayList<Document> query = new ArrayList<>();
		this.buildMachStage(recruitmentNumber, query);

		return query;
	}

	private void buildMachStage(Long recruitmentNumber, ArrayList<Document> query) {

	}

}
