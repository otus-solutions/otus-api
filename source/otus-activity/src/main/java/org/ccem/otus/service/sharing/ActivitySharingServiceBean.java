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
  public String getSharedURL(ActivitySharing activitySharing) {
    try {
      ActivitySharing activitySharingFounded = activitySharingDao.getSharedURL(activitySharing.getActivityId());
      return buildActivitySharedURL(activitySharingFounded);
    }
    catch (DataNotFoundException e){
      activitySharingDao.createSharedURL(activitySharing);
      return buildActivitySharedURL(activitySharing);
    }
  }

  @Override
  public String renovateSharedURL(ActivitySharing activitySharing) throws DataNotFoundException {
    activitySharing.renovate();
    activitySharingDao.renovateSharedURL(activitySharing);
    return buildActivitySharedURL(activitySharing);
  }

  @Override
  public void deleteSharedURL(String activityId) throws DataNotFoundException {
    activitySharingDao.deleteSharedURL(new ObjectId(activityId));
  }

  private String buildActivitySharedURL(ActivitySharing activitySharing){
    //TODO
    return "https://otus.hmg.ccem.ufrgs.br/survey-player/#/?activity="+activitySharing.getActivityId()+
      "&token="+activitySharing.getParticipantToken();
  }

}
