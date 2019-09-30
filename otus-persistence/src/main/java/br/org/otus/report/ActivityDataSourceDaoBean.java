package br.org.otus.report;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceFilters;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceResult;
import org.ccem.otus.persistence.ActivityDataSourceDao;
import org.json.JSONObject;

import com.mongodb.client.AggregateIterable;

import br.org.mongodb.MongoGenericDao;

public class ActivityDataSourceDaoBean extends MongoGenericDao<Document> implements ActivityDataSourceDao {

	private ActivityDataSourceFilters filters;

	private static final String COLLECTION_NAME = "activity";

	public ActivityDataSourceDaoBean() {
		super(COLLECTION_NAME, Document.class);
	}

	@Override
	public ActivityDataSourceResult getResult(Long recruitmentNumber, ActivityDataSource activityDataSource) {

		ActivityDataSourceResult result = null;
		ArrayList<Document> query = activityDataSource.buildQuery(recruitmentNumber);
		AggregateIterable<?> output = collection.aggregate(query);

		for (Object anOutput : output) {
			Document next = (Document) anOutput;
			result = ActivityDataSourceResult.deserialize(new JSONObject(next).toString());
		}

		return result;
	}

	@Override
	public ActivityDataSourceResult getAnswerFilling(String activityId) throws DataNotFoundException {
		ObjectId oid = new ObjectId(activityId);
//		List<Bson> pipeline = new ArrayList<>();
//		pipeline.add(new Document( "$match",new Document("_id", oid)));
//		pipeline.add(ActivityDataSource.buildQueryAnwer());

		Document result = collection.find(new Document("_id", oid)).first();
//		Document result = collection.aggregate().first();

		if (result == null) {
			throw new DataNotFoundException(new Throwable("ParticipantReportActivity not found. Id: " + activityId));
		}
		return ActivityDataSourceResult.deserialize(new JSONObject(result).toString());
	}

}
