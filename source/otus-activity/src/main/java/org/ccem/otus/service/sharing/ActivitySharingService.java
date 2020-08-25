package org.ccem.otus.service.sharing;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;

public interface ActivitySharingService {

  String getSharedLink(ActivitySharing activitySharing) throws DataNotFoundException;

  String recreateSharedLink(ActivitySharing activitySharing) throws DataNotFoundException;

  void deleteSharedLink(String activityId) throws DataNotFoundException;
}
