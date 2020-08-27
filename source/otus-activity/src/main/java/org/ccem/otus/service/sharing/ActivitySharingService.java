package org.ccem.otus.service.sharing;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharingDto;

public interface ActivitySharingService {

  ActivitySharingDto getSharedURL(ActivitySharing activitySharing) throws DataNotFoundException;

  ActivitySharingDto createSharedURL(ActivitySharing activitySharing);

  ActivitySharingDto renovateSharedURL(String activitySharingId) throws DataNotFoundException;

  void deleteSharedURL(String activitySharingId) throws DataNotFoundException;
}
