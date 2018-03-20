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
		this.buildProjectionStage(query);
		this.appendFieldCenterFilter(query);
		
		return query;
	}

	private void buildMachStage(Long recruitmentNumber, ArrayList<Document> query) {
		Document filters = new Document("recruitmentNumber", recruitmentNumber).append("examName", this.filters.getExamName());

		Document matchStage = new Document("$match", filters);
		query.add(matchStage);
	}

	private void buildProjectionStage(ArrayList<Document> query) {
		//TODO: para cada atributo que pode existir ou não, deve ser adicionando uma condição de verificação!
	}

	private void appendFieldCenterFilter(ArrayList<Document> query) {
		if(this.filters.getFieldCenter() != null) {
			
		}
	}

}
