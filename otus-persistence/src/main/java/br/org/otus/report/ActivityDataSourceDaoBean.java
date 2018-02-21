package br.org.otus.report;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.dataSources.ActivityDataSourceResult;
import org.ccem.otus.model.dataSources.ActivityDataSource;
import org.ccem.otus.persistence.ActivityDataSourceDao;
import com.mongodb.client.AggregateIterable;
import br.org.mongodb.MongoGenericDao;

import java.util.ArrayList;
import org.bson.Document;
import org.json.JSONObject;


public class ActivityDataSourceDaoBean extends MongoGenericDao<Document> implements ActivityDataSourceDao {

    private static final String COLLECTION_NAME = "activity";

    public ActivityDataSourceDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public ActivityDataSourceResult getResult(Long recruitmentNumber, ActivityDataSource activityDataSource) throws DataNotFoundException {

        ActivityDataSourceResult result = null;
        ArrayList<Document> query = activityDataSource.builtQuery(recruitmentNumber,activityDataSource);
        AggregateIterable output = collection.aggregate(query);

        for (Object anOutput : output) {
            Document next = (Document) anOutput;
            result = ActivityDataSourceResult.deserialize(new JSONObject(next).toString());
        }

        return result;
    }



}
