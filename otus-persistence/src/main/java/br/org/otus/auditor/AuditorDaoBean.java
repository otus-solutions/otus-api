package br.org.otus.auditor;

import br.org.mongodb.MongoGenericDao;

import org.bson.Document;
import org.ccem.auditor.model.Auditor;
import org.ccem.auditor.persistence.AuditorDao;

import javax.ejb.Local;

@Local(AuditorDao.class)
public class AuditorDaoBean extends MongoGenericDao<Document> implements AuditorDao {
    private static final String COLLECTION_NAME = "auditor";

    public AuditorDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    public void persist(Auditor auditor) {
    	super.persist(Auditor.serialize(auditor));
    }
}
