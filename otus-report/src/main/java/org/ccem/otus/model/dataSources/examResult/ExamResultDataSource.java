package org.ccem.otus.model.dataSources.examResult;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.ReportDataSource;

public class ExamResultDataSource extends ReportDataSource<ExamResultDataSourceResult> {

	private ExamResultDataSourceFilters filters;
	private ArrayList<Document> query;
	private Long recruitmentNumber;

	@Override
	public void addResult(ExamResultDataSourceResult result) {
		super.getResult().add(result);
	}

	@Override
	public ArrayList<Document> builtQuery(Long recruitmentNumber) {
		this.query = new ArrayList<>();
		this.recruitmentNumber = recruitmentNumber;
		this.buildMachStage();

		return this.query;
	}

	private void buildMachStage() {
		Document matchStageFilters = new Document("objectType", "Exam");
		this.appendExamName(matchStageFilters);

		Document matchStage = new Document("$match", matchStageFilters);
		this.query.add(matchStage);

		this.appendLookupExamResultInQuery();
		this.appendMatchRecruitmentNumberInQuery();
		this.appendMatchReleaseDateInQuery();
		this.appendLookupExamSendingLot();
		this.appendMatchStageFieldCenterInQuery();
	}

	private void appendExamName(Document matchStage) {
		if (this.filters.getExamName() != null) {
			matchStage.append("name", this.filters.getExamName());
		}
	}

	private void appendLookupExamResultInQuery() {
		Document lookup = new Document("$lookup", new Document("from", "exam_result").append("localField", "_id").append("foreignField", "examId").append("as", "examResults"));
		this.query.add(lookup);
	}

	private void appendMatchRecruitmentNumberInQuery() {
		Document match = new Document("$match", new Document("examResults.recruitmentNumber", this.recruitmentNumber));
		this.query.add(match);
	}

	private void appendMatchReleaseDateInQuery() {
		if (this.filters.getReleaseDate() != null) {
			Document match = new Document("$match", new Document("examResults.releaseDate", this.filters.getReleaseDate()));
			this.query.add(match);
		}
	}

	private void appendLookupExamSendingLot() {
		Document lookup = new Document("$lookup", new Document("from", "exam_sending_lot").append("localField", "examSendingLotId").append("foreignField", "_id").append("as", "examSendingLot"));
		this.query.add(lookup);
	}

	private void appendMatchStageFieldCenterInQuery() {
		if (this.filters.getFieldCenter() != null) {
			Document match = new Document("$match", new Document("examSendingLot.fieldCenter.acronym", this.filters.getFieldCenter().getAcronym()));
			this.query.add(match);
		}
	}

}
