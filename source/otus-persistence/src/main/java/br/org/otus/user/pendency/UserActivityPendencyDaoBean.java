package br.org.otus.user.pendency;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.model.pendency.UserActivityPendency;
import br.org.otus.user.pendency.builder.UserActivityPendencyQueryBuilder;
import br.org.otus.model.pendency.UserActivityPendencyResponse;
import br.org.otus.persistence.pendency.UserActivityPendencyDao;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserActivityPendencyDaoBean extends MongoGenericDao<Document> implements UserActivityPendencyDao {

  private static final String COLLECTION_NAME = "pendency";
  private static final String REQUESTER_ATTRIBUTE_NAME = "requester";
  private static final String RECEIVER_ATTRIBUTE_NAME = "receiver";

  public UserActivityPendencyDaoBean() { super(COLLECTION_NAME, Document.class);  }

  @Override
  public ObjectId create(UserActivityPendency userActivityPendency) {
    Document parsed = Document.parse(UserActivityPendency.serialize(userActivityPendency));
    collection.insertOne(parsed);
    return parsed.getObjectId("_id");
  }

  @Override
  public void update(ObjectId userActivityPendencyOID, UserActivityPendency userActivityPendency) throws DataNotFoundException {
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
  public void delete(ObjectId oid) throws DataNotFoundException {
    DeleteResult deleteResult = this.collection.deleteOne(eq("_id", oid));
    if (deleteResult.getDeletedCount() == 0) {
      throw new DataNotFoundException(new Throwable("User activity pendency with id { " + oid.toString() + " } not found"));
    }
  }

  @Override
  public UserActivityPendency findByActivityOID(ObjectId activityOID) throws DataNotFoundException {
    Document result = collection.find(eq(UserActivityPendencyQueryBuilder.ACTIVITY_ID_FIELD, activityOID)).first();
    if (result == null) {
      throw new DataNotFoundException("No user activity pendency found for activityOID { " + activityOID  + " }.");
    }
    return UserActivityPendency.deserialize(result.toJson());
  }

  @Override
  public List<UserActivityPendencyResponse> findAllPendenciesToReceiver(String receiverUserEmail) throws DataNotFoundException, MemoryExcededException {
    return listPendencies((new UserActivityPendencyQueryBuilder()).getListAllPendenciesByUserQuery(RECEIVER_ATTRIBUTE_NAME, receiverUserEmail));
  }

  @Override
  public List<UserActivityPendencyResponse> findOpenedPendenciesToReceiver(String receiverUserEmail) throws DataNotFoundException, MemoryExcededException {
    return listPendencies((new UserActivityPendencyQueryBuilder()).getListOpenedPendenciesByUserQuery(RECEIVER_ATTRIBUTE_NAME, receiverUserEmail));
  }

  @Override
  public List<UserActivityPendencyResponse> findDonePendenciesToReceiver(String receiverUserEmail) throws DataNotFoundException, MemoryExcededException {
    return listPendencies((new UserActivityPendencyQueryBuilder()).getListDonePendenciesByUserQuery(RECEIVER_ATTRIBUTE_NAME, receiverUserEmail));
  }

  @Override
  public List<UserActivityPendencyResponse> findAllPendenciesFromRequester(String requesterUserEmail) throws DataNotFoundException, MemoryExcededException {
    return listPendencies((new UserActivityPendencyQueryBuilder()).getListAllPendenciesByUserQuery(REQUESTER_ATTRIBUTE_NAME, requesterUserEmail));
  }

  @Override
  public List<UserActivityPendencyResponse> findOpenedPendenciesFromRequester(String requesterUserEmail) throws DataNotFoundException, MemoryExcededException {
    return listPendencies((new UserActivityPendencyQueryBuilder()).getListOpenedPendenciesByUserQuery(REQUESTER_ATTRIBUTE_NAME, requesterUserEmail));
  }

  @Override
  public List<UserActivityPendencyResponse> findDonePendenciesFromRequester(String requesterUserEmail) throws DataNotFoundException, MemoryExcededException {
    return listPendencies((new UserActivityPendencyQueryBuilder()).getListDonePendenciesByUserQuery(REQUESTER_ATTRIBUTE_NAME, requesterUserEmail));
  }

  private List<UserActivityPendencyResponse> listPendencies(ArrayList<Bson> pipelineQuery) throws DataNotFoundException, MemoryExcededException {

    AggregateIterable<Document> results = this.collection.aggregate(pipelineQuery).allowDiskUse(true);

    if (results == null) {
      throw new DataNotFoundException("No results for user activity pendency.");
    }

    MongoCursor<Document> iterator = results.iterator();
    List<UserActivityPendencyResponse> userActivityPendenciesResponse = new ArrayList<>();
    while (iterator.hasNext()) {
      try {
        Document document = iterator.next();
        userActivityPendenciesResponse.add(UserActivityPendencyResponse.deserialize(document.toJson()));
      }
      catch (OutOfMemoryError e) {
        userActivityPendenciesResponse.clear();
        throw new MemoryExcededException("User activity pendency extraction exceded memory used.");
      }
    }
    return userActivityPendenciesResponse;
  }

}
