package br.org.otus.report;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.ccem.otus.model.dataSources.examResult.ExamResultDataSource;
import org.ccem.otus.model.dataSources.examResult.ExamResultDataSourceResult;
import org.ccem.otus.persistence.ExamResultDataSourceDao;

import com.mongodb.client.AggregateIterable;

import br.org.mongodb.MongoGenericDao;

public class ExamResultDataSourceDaoBean extends MongoGenericDao<Document> implements ExamResultDataSourceDao {

	private static final String COLLECTION_NAME = "exam_result";
	private List<ExamResultDataSourceResult> result;

	public ExamResultDataSourceDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	@Override
	public List<ExamResultDataSourceResult> getResult(Long recruitmentNumber, ExamResultDataSource examDataSource) {

		this.result = new ArrayList<>();
		ArrayList<Document> query = examDataSource.builtQuery(recruitmentNumber);
		AggregateIterable<?> output = collection.aggregate(query);

		for (Object anOutput : output) {
			Document next = (Document) anOutput;
			this.result.add(ExamResultDataSourceResult.deserialize(next.toJson()));
		}

		return this.result;
	}

}
