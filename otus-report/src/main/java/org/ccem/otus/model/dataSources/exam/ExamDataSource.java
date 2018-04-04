package org.ccem.otus.model.dataSources.exam;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.model.dataSources.ReportDataSource;

public class ExamDataSource extends ReportDataSource<ExamDataSourceResult> {

	private ExamDataSourceFilters filters;

	@Override
	public void addResult(ExamDataSourceResult result) {
		super.getResult().add(result);
	}

	@Override
	public ArrayList<Document> builtQuery(Long recruitmentNumber) {
		return this.builtQueryToExamSendingLot(recruitmentNumber);
	}

	public ArrayList<Document> builtQueryToExamResults(ObjectId objectId) {
		ArrayList<Document> query = new ArrayList<>();

		Document examSendingLotId = new Document("$match", new Document("examSendingLotId", objectId));
		query.add(examSendingLotId);

		Document objectType = new Document("$match", new Document("objectType", "Exam"));
		query.add(objectType);

		if (this.filters.getExamName() != null) {
			Document examName = new Document("$match", new Document("name", this.filters.getExamName()));
			query.add(examName);
		}

		Document lookup = new Document("$lookup", new Document("from", "exam_result").append("localField", "_id").append("foreignField", "examId").append("as", "examResults"));
		query.add(lookup);

		Document match = new Document("$match", new Document("examResults.aliquotValid", Boolean.TRUE));
		query.add(match);

		return query;
	}

	private ArrayList<Document> builtQueryToExamSendingLot(Long recruitmentNumber) {
		ArrayList<Document> query = new ArrayList<>();

		Document lookup = new Document("$lookup", new Document("from", "exam_result").append("localField", "_id").append("foreignField", "examSendingLotId").append("as", "exam"));
		query.add(lookup);

		Document objectType = new Document("$match", new Document("exam.objectType", "Exam"));
		query.add(objectType);

		if (this.filters.getExamName() != null) {
			Document examName = new Document("$match", new Document("exam.name", this.filters.getExamName()));
			query.add(examName);
		}

		Document participantRecruitmentNumber = new Document("$match", new Document("exam.recruitmentNumber", recruitmentNumber));
		query.add(participantRecruitmentNumber);

		if (this.filters.getFieldCenter() != null) {
			Document fieldCenters = new Document("$match", new Document("fieldCenter.acronym", this.filters.getFieldCenter()));
			query.add(fieldCenters);
		}

		Document sortResults = new Document("$sort", new Document("realizationDate", 1));
		query.add(sortResults);

		Document limitToResults = new Document("$limit", 1);
		query.add(limitToResults);

		return query;
	}

}
