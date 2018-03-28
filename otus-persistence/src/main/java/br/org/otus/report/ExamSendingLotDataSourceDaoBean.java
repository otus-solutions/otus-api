package br.org.otus.report;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import br.org.mongodb.MongoGenericDao;

public class ExamSendingLotDataSourceDaoBean extends MongoGenericDao<Document> {

	private static final String COLLECTION_NAME = "exam_sending_lot";

	public ExamSendingLotDataSourceDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	public MongoCollection<Document> getCollection() {
		return this.collection;
	}
}
