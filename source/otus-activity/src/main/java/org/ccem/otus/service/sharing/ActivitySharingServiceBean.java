package org.ccem.otus.service.sharing;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharingDto;
import org.ccem.otus.persistence.ActivitySharingDao;

import javax.inject.Inject;

public class ActivitySharingServiceBean implements ActivitySharingService {

  private static final String SURVEY_PLAYER_URL = "SURVEY_PLAYER_URL";

  @Inject
  private ActivitySharingDao activitySharingDao;

  @Override
  public ActivitySharingDto getSharedURL(ActivitySharing activitySharing) throws DataNotFoundException {
    ActivitySharing activitySharingFounded = activitySharingDao.getSharedURL(activitySharing.getActivityId());
    return buildActivitySharingDto(activitySharingFounded);
  }

  @Override
  public ObjectId getActivitySharingIdByActivityId(ObjectId activityId) throws DataNotFoundException {
    try {
      ActivitySharing activitySharingFounded = activitySharingDao.getSharedURL(activityId);
      return activitySharingFounded.getId();
    } catch (DataNotFoundException e) {
      return null;
    }
  }

  @Override
  public ActivitySharingDto createSharedURL(ActivitySharing activitySharing) {
    return buildActivitySharingDto(activitySharingDao.createSharedURL(activitySharing));
  }

  @Override
  public ActivitySharingDto renovateSharedURL(String activitySharingId) throws DataNotFoundException {
    return buildActivitySharingDto(activitySharingDao.renovateSharedURL(new ObjectId(activitySharingId)));
  }

  @Override
  public void deleteSharedURL(String activitySharingId) throws DataNotFoundException {
    activitySharingDao.deleteSharedURL(new ObjectId(activitySharingId));
  }

  private ActivitySharingDto buildActivitySharingDto(ActivitySharing activitySharing){
    return new ActivitySharingDto(activitySharing,
      System.getenv(SURVEY_PLAYER_URL)+"?activity="+activitySharing.getActivityId()+
      "&token="+activitySharing.getParticipantToken());
  }

}
