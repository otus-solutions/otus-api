package br.org.otus.monitoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.persistence.MonitoringDao;

import com.mongodb.client.AggregateIterable;

import br.org.mongodb.MongoGenericDao;

@Stateless
public class MonitoringDaoBean extends MongoGenericDao<Document> implements MonitoringDao {

  public static final String COLLECTION_NAME = "activity";

  public MonitoringDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ArrayList<MonitoringDataSourceResult> get(ArrayList<Document> query) throws ValidationException {

    ArrayList<MonitoringDataSourceResult> monitoringData = new ArrayList<>();
    AggregateIterable output = collection.aggregate(query);

    for (Object anOutput : output) {
      Document result = (Document) anOutput;
      monitoringData.add(MonitoringDataSourceResult.deserialize(result.toJson()));
    }

    return monitoringData;
  }

}
