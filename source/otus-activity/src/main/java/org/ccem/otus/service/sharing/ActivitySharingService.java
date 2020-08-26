package org.ccem.otus.service.sharing;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharingDto;

public interface ActivitySharingService {

  ActivitySharingDto getSharedURL(ActivitySharing activitySharing) throws DataNotFoundException;

  ActivitySharingDto renovateSharedURL(ActivitySharing activitySharing) throws DataNotFoundException;

  void deleteSharedURL(String activityId) throws DataNotFoundException;
}
