package org.ccem.otus.service.sharing;

import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;

public class ActivitySharingServiceBean implements ActivitySharingService {

  @Override
  public String getSharedLink(String activityId) {
    return "https://meu.link"; //TODO
  }

  @Override
  public String recreateSharedLink(ActivitySharing activitySharing) {
    return "https://novo.link"; //TODO
  }

  @Override
  public void deleteSharedLink(String activityId) {

  }

  private String buildActivitySharedURL(ActivitySharing activitySharing){
    return "https://otus.hmg.ccem.ufrgs.br/survey-player/#/?activity="+activitySharing.getActivityID()+
      "&token="+activitySharing.getParticipantToken();
  }

}
