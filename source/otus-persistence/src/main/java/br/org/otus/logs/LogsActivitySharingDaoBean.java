package br.org.otus.logs;

import br.org.mongodb.MongoGenericDao;
import org.ccem.otus.logs.activity.ActivityLog;
import org.ccem.otus.logs.persistence.LogsActivitySharingDao;
import org.bson.Document;

public class LogsActivitySharingDaoBean extends MongoGenericDao<Document> implements LogsActivitySharingDao {
  private static final String COLLECTION_NAME = "logs_activity_sharing";

  public LogsActivitySharingDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(ActivityLog activityLog) {
    Document parsed = Document.parse(ActivityLog.serialize(activityLog));
    this.collection.insertOne(parsed);
  }
}
