package br.org.otus.report;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.ccem.otus.model.ParticipantDataSource;
import org.ccem.otus.persistence.ParticipantDataSourceDao;

public class ParticipantDataSourceDaoBean extends MongoGenericDao<Document> implements ParticipantDataSourceDao{

    private static final String COLLECTION_NAME = "participant";

    public ParticipantDataSourceDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public ParticipantDataSource getResult(ParticipantDataSource participantDataSource) {

        return null;
    }

}
