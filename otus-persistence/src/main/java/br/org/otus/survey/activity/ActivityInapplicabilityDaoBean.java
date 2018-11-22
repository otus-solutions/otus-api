package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.ccem.otus.persistence.ActivityInapplicabilityDao;

public class ActivityInapplicabilityDaoBean extends MongoGenericDao<Document> implements ActivityInapplicabilityDao {

  private static final String COLLECTION_NAME = "survey";

  public ActivityInapplicabilityDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void update(){}

  @Override
  public void delete() {}

}
