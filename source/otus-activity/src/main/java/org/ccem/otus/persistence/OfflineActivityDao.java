package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollectionsDTO;

public interface OfflineActivityDao {
  void persist(OfflineActivityCollection offlineActivityCollection);

  OfflineActivityCollectionsDTO fetchByUserId(ObjectId id) throws DataNotFoundException;
}
