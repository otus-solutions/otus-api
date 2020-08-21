package org.ccem.otus.service.sharing;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;

public interface ActivitySharingService {

  String getSharedLink(String activityID, String token) throws DataNotFoundException;

  String recreateSharedLink(String activityID, String token) throws DataNotFoundException;

  void deleteSharedLink(String activityID, String token) throws DataNotFoundException;
}
