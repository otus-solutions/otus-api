package org.ccem.otus.model.dataSources.exam;

import java.util.ArrayList;
import java.util.Arrays;

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

	public ArrayList<Document> buildQueryToExamResults(ObjectId objectId, Long recruitmentNumber) {
		ArrayList<Document> query = new ArrayList<>();

		Document match = new Document("$match", new Document("examSendingLotId", objectId)
			.append("objectType", "Exam").append("name", this.filters.getExamName())
		);
		query.add(match);

		Document lookup = new Document("$lookup", new Document("from", "exam_result")
			.append("let", new Document("exam_oid", "$_id"))
				.append("pipeline", Arrays.asList(
						new Document("$match", new Document("recruitmentNumber", recruitmentNumber)
								.append("aliquotValid", Boolean.TRUE)
								.append("$expr", new Document("$and", Arrays.asList(
										new Document("$eq", Arrays.asList("$examId", "$$exam_oid"))
								)))
						)
				))
				.append("as", "examResults")
		);
		query.add(lookup);

		Document matchHasExamResult = new Document("$match", new Document("examResults.recruitmentNumber", new Document("$exists", 1)));
		query.add(matchHasExamResult);

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
