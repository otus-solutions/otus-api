package br.org.otus.laboratory.unattached;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import unattached.UnattachedLaboratory;
import unattached.UnattachedLaboratoryDao;

public class UnattachedLaboratoryDaoBean extends MongoGenericDao<Document> implements UnattachedLaboratoryDao {
  private static final String COLLECTION_NAME = "unattached_laboratory";

  public UnattachedLaboratoryDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(UnattachedLaboratory unattachedLaboratory) {
    super.persist(UnattachedLaboratory.serialize(unattachedLaboratory));
  }
}
