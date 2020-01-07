package br.org.otus.user.pendency;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.model.pendency.UserActivityPendency;
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
import org.ccem.otus.service.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserActivityPendencyDaoBean extends MongoGenericDao<Document> implements UserActivityPendencyDao {

  private static final String COLLECTION_NAME = "pendency";
  private static final String CREATED_STATUS = "CREATED";
  private static final String FINALIZED_STATUS = "FINALIZED";

  public UserActivityPendencyDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

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
    Document result = collection.find(eq("activityInfo.id", activityOID)).first();
    if (result == null) {
      throw new DataNotFoundException("No user activity pendency found for activityOID { " + activityOID  + " }.");
    }
    return UserActivityPendency.deserialize(result.toJson());
  }

  @Override
  public List<UserActivityPendency> findAllPendencies(String userEmail) throws DataNotFoundException, MemoryExcededException {
    return listPendencies(userEmail, "", "");
  }

  @Override
  public List<UserActivityPendency> findOpenedPendencies(String userEmail) throws DataNotFoundException, MemoryExcededException {
    return listPendencies(userEmail, CREATED_STATUS, ",\n{ $not: [ { $in: [\"" + FINALIZED_STATUS + "\", \"$statusHistory.name\"] } ] }");
  }

  @Override
  public List<UserActivityPendency> findDonePendencies(String userEmail) throws DataNotFoundException, MemoryExcededException {
    return listPendencies(userEmail, FINALIZED_STATUS, ",\n{ $in: [\"" + FINALIZED_STATUS + "\", \"$statusHistory.name\"] }");
  }

  private List<UserActivityPendency> listPendencies(String userEmail, String statusName, String statusCondition) throws DataNotFoundException, MemoryExcededException {
    List<Bson> pipeline = new ArrayList<>();
    pipeline.add(ParseQuery.toDocument("{\n" +
      "        $lookup: {\n" +
      "            from:\"activity\",\n" +
      "            let: {\n" +
      "                activityInfo_id: \"$activityInfo.id\"\n" +
      "            },\n" +
      "            pipeline: [\n" +
      "                {\n" +
      "                    $match: {\n" +
      "                        $expr: {\n" +
      "                            $and: [\n" +
      "                                { $eq: [\"$$activityInfo_id\", \"$_id\"] },\n" +
      "                                { $eq: [false, \"$isDiscarded\"] }" +
      "                                " + statusCondition +
      "                            ]\n" +
      "                        }\n" +
      "                    }\n" +
      "                }\n" +
      "            ],\n" +
      "            as:\"joinResult\"\n" +
      "        }\n" +
      "    }"));
    pipeline.add(ParseQuery.toDocument("{ \n" +
      "        $match: {\n" +
      "            $expr: { \n" +
      "                $and: [\n" +
      "                    { $eq: [ \"$requester\", " + userEmail + " ] },\n" +
      "                    { $gt: [ { $size: \"$joinResult\"}, 0] }\n" +
      "                ]\n" +
      "            }\n" +
      "        } \n" +
      "    }"));
    pipeline.add(ParseQuery.toDocument("{ $project: { \"joinResult\": 0 } }"));

    pipeline.forEach( pip -> System.out.println(pip + ",\n"));//.

    AggregateIterable<Document> results = this.collection.aggregate(pipeline).allowDiskUse(true);

    if (results == null) {
      throw new DataNotFoundException("There are no results for " + statusName + " user activity pendency.");
    }

    MongoCursor<Document> iterator = results.iterator();
    List<UserActivityPendency> userActivityPendencies = new ArrayList<>();
    while (iterator.hasNext()) {
      try {
        Document document = iterator.next();
        userActivityPendencies.add(UserActivityPendency.deserialize(document.toJson()));
      }
      catch (OutOfMemoryError e) {
        userActivityPendencies.clear();
        throw new MemoryExcededException("User activity pendency extraction exceded memory used.");
      }
    }
    return userActivityPendencies;
  }

}
