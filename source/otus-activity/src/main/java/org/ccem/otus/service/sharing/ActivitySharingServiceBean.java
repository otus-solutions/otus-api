package org.ccem.otus.service.sharing;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharingDto;
import org.ccem.otus.persistence.ActivitySharingDao;

import javax.inject.Inject;

public class ActivitySharingServiceBean implements ActivitySharingService {

  @Inject
  private ActivitySharingDao activitySharingDao;

  @Override
  public ActivitySharingDto getSharedURL(ActivitySharing activitySharing) {
    try {
      ActivitySharing activitySharingFounded = activitySharingDao.getSharedURL(activitySharing.getActivityId());
      return buildActivitySharingDto(activitySharingFounded);
    }
    catch (DataNotFoundException e){
      return buildActivitySharingDto(activitySharingDao.createSharedURL(activitySharing));
    }
  }

  @Override
  public ActivitySharingDto renovateSharedURL(ActivitySharing activitySharing) throws DataNotFoundException {
    activitySharing.renovate();
    return buildActivitySharingDto(activitySharingDao.renovateSharedURL(activitySharing));
  }

  @Override
  public void deleteSharedURL(String activityId) throws DataNotFoundException {
    activitySharingDao.deleteSharedURL(new ObjectId(activityId));
  }

  private ActivitySharingDto buildActivitySharingDto(ActivitySharing activitySharing){
    return new ActivitySharingDto(activitySharing,
      "https://otus.hmg.ccem.ufrgs.br/survey-player/#/?activity="+activitySharing.getActivityId()+
      "&token="+activitySharing.getParticipantToken());
  }

}
