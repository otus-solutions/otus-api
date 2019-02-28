package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.monitoring.builder.ActivityStatusQueryBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.persistence.FlagReportDao;

import java.util.LinkedList;
import java.util.List;

public class FlagReportDaoBean extends MongoGenericDao<Document> implements FlagReportDao {

  public static final String COLLECTION_NAME = "activity";

  public FlagReportDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public Document getActivitiesProgressReport(LinkedList<String> surveyAcronyms) throws DataNotFoundException {
    List<Bson> query = new ActivityStatusQueryBuilder()
        .getActivityStatusQuery(surveyAcronyms);

    Document result = collection.aggregate(query).first();

    if(result == null){
      throw new DataNotFoundException("There are no results");
    }

    return result;
  }

  @Override
  public Document getActivitiesProgressReport(String center, LinkedList<String> surveyAcronyms) throws DataNotFoundException {
    List<Bson> query = new ActivityStatusQueryBuilder()
            .getActivityStatusQuery(center,surveyAcronyms);

    Document result = collection.aggregate(query).first();

    if(result == null){
      throw new DataNotFoundException("There are no results");
    }

    return result;
  }


}