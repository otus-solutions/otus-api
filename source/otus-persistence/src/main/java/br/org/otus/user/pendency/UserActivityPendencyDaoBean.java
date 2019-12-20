package br.org.otus.user.pendency;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.model.pendency.UserActivityPendency;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

/* ****************************************
 ver metodos das classes
 ActivityConfigurationDaoBean
 ParticipantDaoBean
*/

public class UserActivityPendencyDaoBean extends MongoGenericDao<Document> implements UserActivityPendencyDao {

  private static final String COLLECTION_NAME = "pendency";

  public UserActivityPendencyDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void create(UserActivityPendency userActivityPendency) {
    Document parsed = Document.parse(UserActivityPendency.serialize(userActivityPendency));
    this.collection.insertOne(parsed);
  }

  @Override
  public void update(Object data) throws ValidationException, DataNotFoundException {
    throw new NotImplementedException();
  }

  @Override
  public ArrayList<UserActivityPendency> find() {
    throw new NotImplementedException();
  }

  @Override
  public void delete(String name) throws DataNotFoundException {
    throw new NotImplementedException();
  }

  @Override
  public boolean exists(String userActivityPendencyId) {
    Document result = this.collection.find(eq("activityInfo.id", userActivityPendencyId)).first();
    return (result != null);
  }
}
