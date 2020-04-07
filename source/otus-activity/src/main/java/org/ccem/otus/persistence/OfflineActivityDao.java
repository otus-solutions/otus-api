package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollectionGroupsDTO;

import java.util.List;

public interface OfflineActivityDao {
  void persist(OfflineActivityCollection offlineActivityCollection);

  OfflineActivityCollectionGroupsDTO fetchByUserId(ObjectId id) throws DataNotFoundException;

  OfflineActivityCollection fetchCollection(String collectionId) throws DataNotFoundException;

  void deactivateOfflineActivityCollection(String offlineCollectionId, List<ObjectId> createdActivityIds);
}
