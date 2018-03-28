package org.ccem.otus.model.dataSources.exam;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.model.dataSources.ReportDataSource;

public class ExamResultDataSource extends ReportDataSource<ExamDataSourceResult> {

	private ExamResultDataSourceFilters filters;
	private ArrayList<Document> query;
	private Long recruitmentNumber;

	@Override
	public void addResult(ExamDataSourceResult result) {
		super.getResult().add(result);
	}

	@Override
	public ArrayList<Document> builtQuery(Long recruitmentNumber) {
		this.query = new ArrayList<>();
		this.recruitmentNumber = recruitmentNumber;

		this.builtQueryToExamSendingLot(recruitmentNumber);

		return this.query;
	}

	private ArrayList<Document> builtQueryToExamSendingLot(Long recruitmentNumber) {
		this.query = new ArrayList<>();

		Document lookup = new Document("$lookup", new Document("from", "exam_result").append("localField", "_id").append("foreignField", "examSendingLotId").append("as", "exam"));
		this.query.add(lookup);

		Document objectType = new Document("$match", new Document("exam.objectType", "Exam"));
		this.query.add(objectType);

		if (this.filters.getExamName() != null) {
			Document examName = new Document("$match", new Document("exam.name", this.filters.getExamName()));
			this.query.add(examName);
		}

		Document participantRecruitmentNumber = new Document("$match", new Document("exam.recruitmentNumber", this.recruitmentNumber));
		this.query.add(participantRecruitmentNumber);

		Document sortResults = new Document("$sort", new Document("service.apps.updates.date", 1));
		this.query.add(sortResults);

		Document limitToResults = new Document("$limit", 1);
		this.query.add(limitToResults);

		return this.query;
	}

	public ArrayList<Document> builtQueryToExamResults(ObjectId objectId) {
		this.query = new ArrayList<>();

		Document examSendingLotId = new Document("$match", new Document("examSendingLotId", objectId));
		this.query.add(examSendingLotId);

		Document objectType = new Document("$match", new Document("objectType", "Exam"));
		this.query.add(objectType);

		if (this.filters.getExamName() != null) {
			Document examName = new Document("$match", new Document("name", this.filters.getExamName()));
			this.query.add(examName);
		}

		Document lookup = new Document("$lookup", new Document("from", "exam_result").append("localField", "_id").append("foreignField", "examId").append("as", "examResults"));
		this.query.add(lookup);

		return this.query;
	}

}
