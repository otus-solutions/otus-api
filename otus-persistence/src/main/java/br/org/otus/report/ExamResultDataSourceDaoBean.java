package br.org.otus.report;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.examResult.ExamResultDataSource;
import org.ccem.otus.model.dataSources.examResult.ExamResultDataSourceResult;
import org.ccem.otus.persistence.ExamResultDataSourceDao;
import org.json.JSONObject;

import com.mongodb.client.AggregateIterable;

import br.org.mongodb.MongoGenericDao;

public class ExamResultDataSourceDaoBean extends MongoGenericDao<Document> implements ExamResultDataSourceDao {

	private static final String COLLECTION_NAME = "exam_result";

	public ExamResultDataSourceDaoBean(String collectionName, Class<Document> clazz) {
		super(COLLECTION_NAME, Document.class);
	}

	@Override
	public ExamResultDataSourceResult getResult(Long recruitmentNumber, ExamResultDataSource examDataSource) {
		ExamResultDataSourceResult result;
		ArrayList<Document> query = examDataSource.builtQuery(recruitmentNumber);
		AggregateIterable output = collection.aggregate(query);

		for (Object anOutput : output) {
			Document next = (Document) anOutput;
			result = ExamResultDataSourceResult.deserialize(new JSONObject(next).toString());
		}

		return null;
	}

}
