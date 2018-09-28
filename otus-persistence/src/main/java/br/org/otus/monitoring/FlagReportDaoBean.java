package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.monitoring.builder.ActivityStatusQueryBuilder;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.model.monitoring.ActivitiesProgressionReport;
import org.ccem.otus.persistence.FlagReportDao;

import java.util.ArrayList;
import java.util.List;

public class FlagReportDaoBean extends MongoGenericDao<Document> implements FlagReportDao {

  public static final String COLLECTION_NAME = "activity";

  public FlagReportDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }


  @Override
  public ArrayList<ActivitiesProgressionReport> getActivitiesProgressionReport() {
    List<Bson> query = new ActivityStatusQueryBuilder()
      .project()
      .project2()
      .groupByParticipant()
      .projecta()
      .build();

    MongoCursor<Document> iterator = collection.aggregate(query).iterator();

    return deserializeReport(iterator);
  }

  @Override
  public ArrayList<ActivitiesProgressionReport> getActivitiesProgressionReport(String center) {
    List<Bson> query = new ActivityStatusQueryBuilder()
      .matchFieldCenter(center)
      .project()
      .project2()
      .groupByParticipant()
      .projecta()
      .build();

    MongoCursor<Document> iterator = collection.aggregate(query).iterator();

    return deserializeReport(iterator);
  }

  private ArrayList<ActivitiesProgressionReport> deserializeReport (MongoCursor<Document> iterator) {

    ArrayList<ActivitiesProgressionReport> reports = new ArrayList<>();

    try {
      while (iterator.hasNext()) {
        reports.add(
          ActivitiesProgressionReport.deserialize(iterator.next().toJson())
        );
      }
    } finally {
      iterator.close();
    }

    return reports;
  }

}