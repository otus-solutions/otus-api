package br.org.otus.report;

import java.util.ArrayList;

import org.bson.Document;
import org.ccem.otus.model.dataSources.activity.ActivityDataSource;
import org.ccem.otus.model.dataSources.activity.ActivityDataSourceResult;
import org.ccem.otus.persistence.ActivityDataSourceDao;
import org.json.JSONObject;

import com.mongodb.client.AggregateIterable;

import br.org.mongodb.MongoGenericDao;

public class ActivityDataSourceDaoBean extends MongoGenericDao<Document> implements ActivityDataSourceDao {

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
}
