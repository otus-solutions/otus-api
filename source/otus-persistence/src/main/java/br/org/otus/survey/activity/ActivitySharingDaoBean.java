package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.persistence.ActivitySharingDao;

import static com.mongodb.client.model.Filters.eq;

public class ActivitySharingDaoBean extends MongoGenericDao<Document> implements ActivitySharingDao {

  public static final String COLLECTION_NAME = "activity_sharing";

  public static final String OID_KEY_NAME = "_id";
  public static final String ACTIVITY_ID_FIELD_NAME = "activityId";
  public static final String EXPIRATION_DATE_FIELD_NAME = "expirationDate";

  public ActivitySharingDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ActivitySharing getSharedURL(ObjectId activityOID) throws DataNotFoundException {
    Document result = collection.find(eq(ACTIVITY_ID_FIELD_NAME, activityOID)).first();
    if(result == null){
      throw new DataNotFoundException(new Throwable(
        "No activity shared link found for " + ACTIVITY_ID_FIELD_NAME + " " + activityOID.toString()));
    }
    return ActivitySharing.deserialize(result.toJson());
  }

  @Override
  public ObjectId createSharedURL(ActivitySharing activitySharing) {
    Document result = collection.find(eq(ACTIVITY_ID_FIELD_NAME, activitySharing.getActivityId())).first();
    if(result != null){
      return result.getObjectId(OID_KEY_NAME);
    }

    Document parsed = Document.parse(ActivitySharing.serialize(activitySharing));
    collection.insertOne(parsed);
    return parsed.getObjectId(OID_KEY_NAME);
  }

  @Override
  public void renovateSharedURL(ActivitySharing activitySharing) throws DataNotFoundException {
    UpdateResult updateResult = collection.updateOne(
      eq(ACTIVITY_ID_FIELD_NAME, activitySharing.getActivityId()),
      new Document("$set", new Document(EXPIRATION_DATE_FIELD_NAME, activitySharing.getExpirationDate()))
    );

    if(updateResult.getMatchedCount() == 0){
      throw new DataNotFoundException(new Throwable(
        "No activity shared link found for " + ACTIVITY_ID_FIELD_NAME + " " + activitySharing.getActivityId().toString()));
    }
  }

  @Override
  public void deleteSharedURL(ObjectId activityOID) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(eq(ACTIVITY_ID_FIELD_NAME, activityOID));
    if(deleteResult == null){
      throw new DataNotFoundException(new Throwable(
        "No activity shared link found for " + ACTIVITY_ID_FIELD_NAME + " " + activityOID.toString()));
    }
  }

}
