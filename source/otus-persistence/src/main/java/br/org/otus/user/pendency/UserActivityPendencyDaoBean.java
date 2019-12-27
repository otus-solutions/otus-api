package br.org.otus.user.pendency;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.persistence.pendency.UserActivityPendencyDao;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

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
  public void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws ValidationException, DataNotFoundException {
    UpdateResult updateOne1 = collection.updateOne(
      eq("_id", userActivityPendencyOID),
      new Document("$set", new Document("dueDate", userActivityPendency.getDueDate()))
    );

    UpdateResult updateOne2 = collection.updateOne(
      eq("_id", userActivityPendencyOID),
      new Document("$set", new Document("receiver", userActivityPendency.getReceiver()))
    );

    if (updateOne1.getMatchedCount() == 0 || updateOne2.getMatchedCount() == 0) {
      throw new DataNotFoundException(new Throwable("OID {" + userActivityPendencyOID.toString() + "} not found."));
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
  public UserActivityPendency findByActivityInfo(String activityId) throws DataNotFoundException {
    Document result = collection.find(eq("activityInfo.id", activityId)).first();

    if (result == null) {
      throw new DataNotFoundException("No user activity pendency found for activity { " + activityId  + " }.");
    }

    return UserActivityPendency.deserialize(result.toJson());
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
