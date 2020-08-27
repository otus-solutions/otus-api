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
        "No activity shared url found for " + ACTIVITY_ID_FIELD_NAME + " " + activityOID.toString()));
    }
    return ActivitySharing.deserialize(result.toJson());
  }

  @Override
  public ActivitySharing createSharedURL(ActivitySharing activitySharing) {
    Document result = collection.find(eq(ACTIVITY_ID_FIELD_NAME, activitySharing.getId())).first();
    if(result != null){
      return activitySharing;
    }

    Document parsed = Document.parse(ActivitySharing.serialize(activitySharing));
    collection.insertOne(parsed);
    activitySharing.setId(parsed.getObjectId(OID_KEY_NAME));
    return activitySharing;
  }

  @Override
  public ActivitySharing renovateSharedURL(ObjectId activitySharingOID) throws DataNotFoundException {
    Document result = collection.find(eq(OID_KEY_NAME, activitySharingOID)).first();
    if(result == null){
      throw new DataNotFoundException(new Throwable(
        "No activity shared url found with id " + activitySharingOID.toString()));
    }

    ActivitySharing activitySharing = ActivitySharing.deserialize(result.toJson());
    activitySharing.renovate();

    collection.updateOne(
      eq(OID_KEY_NAME, activitySharingOID),
      new Document("$set", new Document(EXPIRATION_DATE_FIELD_NAME, activitySharing.getExpirationDate()))
    );

    return activitySharing;
  }

  @Override
  public void deleteSharedURL(ObjectId activitySharingOID) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(eq(OID_KEY_NAME, activitySharingOID));
    if(deleteResult == null){
      throw new DataNotFoundException(new Throwable(
        "No activity shared url found with id" + activitySharingOID.toString()));
    }
  }

}
