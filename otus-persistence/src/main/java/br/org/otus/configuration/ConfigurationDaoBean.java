package br.org.otus.configuration;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.persistence.MonitoringDao;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ConfigurationDaoBean extends MongoGenericDao<Document>{
    public static final String COLLECTION_NAME = "activity";

    public ConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }


    void enableNewParticipants(boolean permission) {
    }

//    ProjectConfiguration getProjectConfiguration() {
//    }

    public List<MonitoringDataSourceResult> get(List<Document> query) throws ValidationException {

        List<MonitoringDataSourceResult> monitoringData = new ArrayList<>();
        AggregateIterable output = collection.aggregate(query);

        for (Object anOutput : output) {
            Document result = (Document) anOutput;
            monitoringData.add(MonitoringDataSourceResult.deserialize(result.toJson()));
        }

        return monitoringData;
    }

}
