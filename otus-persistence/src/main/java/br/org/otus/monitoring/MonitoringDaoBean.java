package br.org.otus.monitoring;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.monitoring.builder.ActivityStatusQueryBuilder;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.persistence.MonitoringDao;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MonitoringDaoBean extends MongoGenericDao<Document> implements MonitoringDao {

  public static final String COLLECTION_NAME = "activity";

  public MonitoringDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public List<MonitoringDataSourceResult> get(List<Document> query) throws ValidationException {

    List<MonitoringDataSourceResult> monitoringData = new ArrayList<>();
    AggregateIterable output = collection.aggregate(query);

    for (Object anOutput : output) {
      Document result = (Document) anOutput;
      monitoringData.add(MonitoringDataSourceResult.deserialize(result.toJson()));
    }

    return monitoringData;
  }

  @Override
  public void getActivitiesProgressionReport() {
    List<Bson> query = new ActivityStatusQueryBuilder()
      .matchFieldCenter("SP")
      .project()
      .project2()
      .groupByParticipant()
      .build();

    collection.aggregate(query).iterator();

    System.out.println();
  }

}
