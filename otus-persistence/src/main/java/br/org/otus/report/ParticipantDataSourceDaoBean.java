package br.org.otus.report;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.json.JSONObject;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.dataSources.ParticipantDataSource;
import org.ccem.otus.model.dataSources.ParticipantDataSourceResult;
import org.ccem.otus.persistence.FieldCenterDao;
import org.ccem.otus.persistence.ParticipantDataSourceDao;

import javax.inject.Inject;

import static com.mongodb.client.model.Filters.eq;

public class ParticipantDataSourceDaoBean extends MongoGenericDao<Document> implements ParticipantDataSourceDao {

    private static final String COLLECTION_NAME = "participant";

    @Inject
    private FieldCenterDao fieldCenterDao;

    public ParticipantDataSourceDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public ParticipantDataSourceResult getResult(long recruitmentNumber, ParticipantDataSource participantDataSource) throws DataNotFoundException{
        Document result = this.collection.find(eq("recruitmentNumber", recruitmentNumber)).first();
        if (result == null) {
            throw new DataNotFoundException(
                    new Throwable("Participant with recruitment number {" + recruitmentNumber + "} not found."));
        }

        return ParticipantDataSourceResult.deserialize(new JSONObject(result).toString());
    }

}
