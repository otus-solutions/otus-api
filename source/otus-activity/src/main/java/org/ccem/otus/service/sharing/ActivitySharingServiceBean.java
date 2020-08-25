package org.ccem.otus.service.sharing;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.persistence.ActivitySharingDao;

import javax.inject.Inject;

public class ActivitySharingServiceBean implements ActivitySharingService {

  @Inject
  private ActivitySharingDao activitySharingDao;

  @Override
  public String getSharedLink(ActivitySharing activitySharing) {
    try {
      ActivitySharing activitySharingFounded = activitySharingDao.getSharedLink(activitySharing.getActivityID());
      return buildActivitySharedURL(activitySharingFounded);
    }
    catch (DataNotFoundException e){
      return recreateSharedLink(activitySharing);
    }
//    return "https://meu.link"; //TODO
  }

  @Override
  public String recreateSharedLink(ActivitySharing activitySharing) {
    activitySharingDao.recreateSharedLink(activitySharing);
    return buildActivitySharedURL(activitySharing);
//    return "https://novo.link"; //TODO
  }

  @Override
  public void deleteSharedLink(String activityId) throws DataNotFoundException {
    activitySharingDao.deleteSharedLink(new ObjectId(activityId));
  }

  private String buildActivitySharedURL(ActivitySharing activitySharing){
    //TODO
    return "https://otus.hmg.ccem.ufrgs.br/survey-player/#/?activity="+activitySharing.getActivityID()+
      "&token="+activitySharing.getParticipantToken();
  }

}
