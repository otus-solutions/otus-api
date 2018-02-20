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

import java.util.Arrays;
import java.util.List;


public class ActivityDataSourceDaoBean extends MongoGenericDao<Document> implements ActivityDataSourceDao {

    private static final String COLLECTION_NAME = "activity";

    public ActivityDataSourceDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public ActivityDataSourceResult getResult(Long recruitmentNumber, ActivityDataSource activityDataSource) throws DataNotFoundException {
        List<Document> query = new ArrayList<>();
        ActivityDataSourceResult result = null;
        Document filters = new Document("participantData.recruitmentNumber", recruitmentNumber)
                            .append("surveyForm.surveyTemplate.identity.acronym", activityDataSource.getAcronym());

        if(activityDataSource.getCategory() != null){
            appendCategoryFilter(filters, activityDataSource.getCategory());
        }

        Document matchStage = new Document("$match",filters);
        Document projectStage = new Document("$project",new Document("statusHistory",new Document("$filter",new Document("input","$statusHistory").append("as","statusHistory").append("cond",new Document("$$statusHistory.name","FINALIZED")))));
        AggregateIterable output = collection.aggregate(Arrays.asList(matchStage,projectStage));

        for (Object anOutput : output) {
            Document next = (Document) anOutput;
            result = ActivityDataSourceResult.deserialize(new JSONObject(next).toString());
        }

        return result;
    }

    private void appendCategoryFilter(Document matchStage, String category) {
        matchStage.append("category.name",category);
    }

}
