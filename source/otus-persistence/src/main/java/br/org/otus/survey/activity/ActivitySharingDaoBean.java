package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.persistence.ActivitySharingDao;

import static com.mongodb.client.model.Filters.eq;

public class ActivitySharingDaoBean extends MongoGenericDao<Document> implements ActivitySharingDao {

  public static final String COLLECTION_NAME = "activity_sharing";//TODO
  public static final String OBJECT_TYPE = "ActivitySharing";//TODO

  public static final String OID_KEY_NAME = "_id";
  public static final String ACTIVITY_ID_FIELD_NAME = "activityId";

  public ActivitySharingDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ActivitySharing getSharedLink(ObjectId activityOID) throws DataNotFoundException {
    Document result = collection.find(eq(ACTIVITY_ID_FIELD_NAME, activityOID)).first();
    if(result == null){
      throw new DataNotFoundException(new Throwable(
        "No activity shared link found for " + ACTIVITY_ID_FIELD_NAME + " " + activityOID.toString()));
    }
    return ActivitySharing.deserialize(result.toJson());
  }

  @Override
  public ObjectId recreateSharedLink(ActivitySharing activitySharing) {
    activitySharing.setObjectType(OBJECT_TYPE);
    Document parsed = Document.parse(ActivitySharing.serialize(activitySharing));
    collection.insertOne(parsed);
    return parsed.getObjectId(OID_KEY_NAME);
  }

  @Override
  public void deleteSharedLink(ObjectId activityOID) throws DataNotFoundException {
    DeleteResult deleteResult = collection.deleteOne(eq(ACTIVITY_ID_FIELD_NAME, activityOID));
    if(deleteResult == null){
      throw new DataNotFoundException(new Throwable(
        "No activity shared link found for " + ACTIVITY_ID_FIELD_NAME + " " + activityOID.toString()));
    }
  }

}
