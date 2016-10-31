package br.org.otus.participant;

import br.org.mongodb.MongoGenericDao;
import com.google.gson.Gson;
import org.ccem.otus.model.Participant;
import org.ccem.otus.persistence.ParticipantDao;

public class ParticipantDaoBean extends MongoGenericDao implements ParticipantDao {
    private static final String COLLECTION_NAME = "Participant";

    public ParticipantDaoBean() {
        super(COLLECTION_NAME);
    }

    @Override
    public void persist(Participant participant) {
        super.persist(new Gson().toJson(participant));
    }
}
