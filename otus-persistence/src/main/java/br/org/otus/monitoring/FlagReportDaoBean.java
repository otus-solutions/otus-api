package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.monitoring.builder.ActivityStatusQueryBuilder;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.persistence.FlagReportDao;

import java.util.ArrayList;
import java.util.List;

public class FlagReportDaoBean extends MongoGenericDao<Document> implements FlagReportDao {

  public static final String COLLECTION_NAME = "activity";

  public FlagReportDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void getActivitiesProgressionReport() {
    List<Bson> query = new ActivityStatusQueryBuilder()
      .matchFieldCenter("SP")
      .project()
      .project2()
      .groupByParticipant()
      .build();

    MongoCursor<Document> iterator = collection.aggregate(query).iterator();

    try {
      while (iterator.hasNext()) {
        iterator.next();
      }
    } finally {
      iterator.close();
    }

    System.out.println(into);
  }

}