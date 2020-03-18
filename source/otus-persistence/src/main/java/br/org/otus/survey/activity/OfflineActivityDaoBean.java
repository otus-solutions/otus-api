package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.ccem.otus.model.survey.activity.OfflineActivityCollection;
import org.ccem.otus.persistence.OfflineActivityDao;

public class OfflineActivityDaoBean extends MongoGenericDao<Document> implements OfflineActivityDao {
  public static final String COLLECTION_NAME = "offline_activity_collection";

  public OfflineActivityDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(OfflineActivityCollection offlineActivityCollection) {
    Document parsed = Document.parse(OfflineActivityCollection.serializeToJsonString(offlineActivityCollection));
    parsed.remove("_id");
    collection.insertOne(parsed);
  }
}
