package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollectionGroupsDTO;
import org.ccem.otus.persistence.OfflineActivityDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OfflineActivityDaoBean extends MongoGenericDao<Document> implements OfflineActivityDao {
  public static final String COLLECTION_NAME = "offline_activity_collection";

  public OfflineActivityDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void persist(OfflineActivityCollection offlineActivityCollection) {
    Document parsed = Document.parse(OfflineActivityCollection.serializeToJsonString(offlineActivityCollection));
    parsed.remove("_id");
    collection.insertOne(parsed);
  }

  @Override
  public OfflineActivityCollectionGroupsDTO fetchByUserId(ObjectId userId) throws DataNotFoundException {
    Document userOfflineCollections = collection.aggregate(Arrays.asList(
      new Document("$match", new Document("userId", userId).append("availableToSynchronize",true)),
      new Document("$group", new Document("_id",new Document("groupId","$groupId").append("groupObservation","$groupObservation"))
        .append("collections",new Document("$push","$$ROOT"))),
      new Document("$group", new Document("_id","")
        .append("offlineActivityCollectionGroups",new Document("$push",new Document("observation","$_id.groupObservation").append("collections","$collections"))))
    )).first();

    if (userOfflineCollections == null) {
      throw new DataNotFoundException(new Throwable("User do not have any offline collection"));
    }

    return OfflineActivityCollectionGroupsDTO.deserialize(userOfflineCollections.toJson());
  }

  @Override
  public OfflineActivityCollection fetchCollection(String collectionId) throws DataNotFoundException {
    Document userOfflineCollections = collection.find(
      new Document("_id", new ObjectId(collectionId))
    ).first();

    if (userOfflineCollections == null) {
      throw new DataNotFoundException(new Throwable("Offline collection not found"));
    }

    return OfflineActivityCollection.deserialize(userOfflineCollections.toJson());
  }

  @Override
  public void deactivateOfflineActivityCollection(String offlineCollectionId, List<ObjectId> createdActivityIds) {
    collection.updateOne(
      new Document("_id", new ObjectId(offlineCollectionId)),
      new Document("$set",new Document("availableToSynchronize",false).append("activities", new ArrayList<>()).append("createdActivityIds",createdActivityIds))
    );
  }
}
