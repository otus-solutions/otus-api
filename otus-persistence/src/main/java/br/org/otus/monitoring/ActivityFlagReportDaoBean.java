package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.monitoring.builder.ActivityStatusQueryBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import org.bson.BSON;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;
import org.ccem.otus.persistence.ActivityFlagReportDao;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityFlagReportDaoBean extends MongoGenericDao<Document> implements ActivityFlagReportDao {

  public static final String COLLECTION_NAME = "activity";

  public ActivityFlagReportDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public Document getActivitiesProgressReport(LinkedList<String> surveyAcronyms) throws DataNotFoundException {
    List<Bson> query = new ActivityStatusQueryBuilder()
        .getActivityStatusQuery(surveyAcronyms);

      return getDocument(query);
  }

    @Override
    public Document getActivitiesProgressReport(String center, LinkedList<String> surveyAcronyms) throws DataNotFoundException {
        List<Bson> query = new ActivityStatusQueryBuilder()
                .getActivityStatusQuery(center,surveyAcronyms);
        return getDocument(query);
    }


    @Override
  public Document getActivitiesProgressReportWithInapplicability(String center, LinkedList<String> surveyAcronyms, List<Document> activityInapplicabilities) throws DataNotFoundException {


      List<Bson> query = new ActivityStatusQueryBuilder()
            .getActivityStatusQueryWithInapplicability(center,surveyAcronyms, activityInapplicabilities);

      return getDocument(query);
  }

 @NotNull
 private Document getDocument(List<Bson> query) throws DataNotFoundException {
    Document result = collection.aggregate(query).allowDiskUse(true).first();

    if(result == null){
      throw new DataNotFoundException("There are no results");
    }

    return result;
 }


}