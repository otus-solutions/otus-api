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
	public ArrayList<Document> buildQuery(Long recruitmentNumber) {
		return this.buildQueryToExamSendingLot(recruitmentNumber);
	}

	public ArrayList<Document> buildQueryToExamResults(ObjectId objectId) {
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

	private ArrayList<Document> buildQueryToExamSendingLot(Long recruitmentNumber) {
		ArrayList<Document> query = new ArrayList<>();

        Document match = new Document(
            "$match",
            new Document("objectType", "ExamResults")
                .append("examName", this.filters.getExamName())
                .append("recruitmentNumber", recruitmentNumber)
        );
        query.add(match);

        Document lookup = new Document(
            "$lookup",
            new Document("from", "exam_sending_lot")
                .append("localField", "examSendingLotId")
                .append("foreignField", "_id")
                .append("as", "sendingLot")
        );
        query.add(lookup);

        Document matchFieldCenter = new Document("$match", new Document("sendingLot.fieldCenter.acronym", this.filters.getFieldCenter()));
        query.add(matchFieldCenter);

        Document sort = new Document("$sort", new Document("sendingLot.realizationDate", 1));
        query.add(sort);

        Document limitToResults = new Document("$limit", 1);
		query.add(limitToResults);

        Document unwind = new Document("$unwind", new Document("path", "$sendingLot"));
        query.add(unwind);

        Document replaceRoot = new Document("$replaceRoot", new Document("newRoot", "$sendingLot"));
        query.add(replaceRoot);

		return query;
	}

}
