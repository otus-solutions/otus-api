package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;

public interface ActivitySharingDao {

  ActivitySharing getSharedLink(ObjectId activityOID) throws DataNotFoundException;

  ObjectId recreateSharedLink(ActivitySharing activitySharing);

  void deleteSharedLink(ObjectId activityOID) throws DataNotFoundException;

}
