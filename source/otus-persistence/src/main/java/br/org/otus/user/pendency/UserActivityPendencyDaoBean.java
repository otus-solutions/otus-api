package br.org.otus.user.pendency;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.persistence.pendency.UserActivityPendencyDao;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
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
  public ObjectId create(UserActivityPendency userActivityPendency) {
    Document parsed = Document.parse(UserActivityPendency.serialize(userActivityPendency));
    this.collection.insertOne(parsed);
    return parsed.getObjectId("_id");
  }

  @Override
  public void update(UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException {
    Document parsed = Document.parse(UserActivityPendency.serialize(userActivityPendency));
    //parsed.remove("_id");
    UpdateResult updateOne = collection.updateOne(
      eq("_id", userActivityPendency.getId()),
      new Document("$set", parsed), new UpdateOptions().upsert(false));

    if (updateOne.getMatchedCount() == 0) {
      throw new DataNotFoundException(new Throwable("OID {" + userActivityPendency.getId().toString() + "} not found."));
    }
  }

  @Override
  public ArrayList<UserActivityPendency> find() throws DataNotFoundException, MemoryExcededException {
    ArrayList<UserActivityPendency> userActivityPendencies = new ArrayList<>();

    FindIterable<Document> find = this.collection.find();

    MongoCursor<Document> iterator = find.iterator();
    while (iterator.hasNext()) {
      try {
        Document document = iterator.next();
        UserActivityPendency userActivityPendency = UserActivityPendency.deserialize(document.toJson());
        userActivityPendencies.add(userActivityPendency);
      } catch (OutOfMemoryError e) {
        userActivityPendencies.clear();
        throw new MemoryExcededException("User activity pendency extraction exceded memory used.");
      }
    }

    return userActivityPendencies;
  }

  @Override
  public void delete(String name) throws DataNotFoundException {
    throw new NotImplementedException();
  }

  @Override
  public boolean exists(String activityInfoId) {
    Document result = this.collection.find(eq("activityInfo.id", activityInfoId)).first();
    return (result != null);
  }
}
