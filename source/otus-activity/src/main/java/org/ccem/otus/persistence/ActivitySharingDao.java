package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;

public interface ActivitySharingDao {

  ActivitySharing getSharedURL(ObjectId activityOID) throws DataNotFoundException;

  ActivitySharing createSharedURL(ActivitySharing activitySharing);

  ActivitySharing renovateSharedURL(ActivitySharing activitySharing) throws DataNotFoundException;

  void deleteSharedURL(ObjectId activityOID) throws DataNotFoundException;

}
