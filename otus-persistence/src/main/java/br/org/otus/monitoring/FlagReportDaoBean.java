package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.monitoring.builder.ActivityStatusQueryBuilder;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.model.monitoring.ActivitiesProgressReport;
import org.ccem.otus.persistence.FlagReportDao;

import java.util.ArrayList;
import java.util.List;

public class FlagReportDaoBean extends MongoGenericDao<Document> implements FlagReportDao {

  public static final String COLLECTION_NAME = "activity";

  public FlagReportDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }


  @Override
  public ArrayList<ActivitiesProgressReport> getActivitiesProgressionReport() {
    List<Bson> query = new ActivityStatusQueryBuilder()
        .projectLastStatus()
        .getStatusValue()
        .sortByDate()
        .removeStatusDate()
        .groupByParticipant()
        .projectId()
        .build();

    MongoCursor<Document> iterator = collection.aggregate(query).iterator();

    return deserializeReport(iterator);
  }

  @Override
  public ArrayList<ActivitiesProgressReport> getActivitiesProgressionReport(String center) {
    List<Bson> query = new ActivityStatusQueryBuilder()
        .matchFieldCenter(center)
        .projectLastStatus()
        .getStatusValue()
        .sortByDate()
        .removeStatusDate()
        .groupByParticipant()
        .projectId()
        .build();

    MongoCursor<Document> iterator = collection.aggregate(query).iterator();

    return deserializeReport(iterator);
  }

  private ArrayList<ActivitiesProgressReport> deserializeReport(MongoCursor<Document> iterator) {

    ArrayList<ActivitiesProgressReport> reports = new ArrayList<>();

    try {
      while (iterator.hasNext()) {
        reports.add(
            ActivitiesProgressReport.deserialize(iterator.next().toJson())
        );
      }
    } finally {
      iterator.close();
    }

    return reports;
  }

}