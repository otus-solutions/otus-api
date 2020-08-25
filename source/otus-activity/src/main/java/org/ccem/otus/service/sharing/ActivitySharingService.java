package org.ccem.otus.service.sharing;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;

public interface ActivitySharingService {

  String getSharedURL(ActivitySharing activitySharing) throws DataNotFoundException;

  String recreateSharedURL(ActivitySharing activitySharing) throws DataNotFoundException;

  void deleteSharedURL(String activityId) throws DataNotFoundException;
}
