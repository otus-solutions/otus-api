package org.ccem.otus.service.sharing;

import org.ccem.otus.model.survey.activity.SurveyActivity;

public class ActivitySharingServiceBean implements ActivitySharingService {

  @Override
  public String getSharedLink(String activityID, String token) {
    return "https://meu.link"; //TODO
  }

  @Override
  public String recreateSharedLink(String activityID, String token) {
    return "https://novo.link"; //TODO
  }

  @Override
  public void deleteSharedLink(String activityID, String token) {

  }

}
