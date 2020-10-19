package org.ccem.otus.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.otus.model.User;
import br.org.otus.persistence.UserDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.dto.StageSurveyActivitiesDto;
import org.ccem.otus.model.survey.activity.status.ActivityStatusOptions;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollection;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.dto.CheckerUpdatedDTO;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.model.survey.offlineActivity.OfflineActivityCollectionGroupsDTO;
import org.ccem.otus.persistence.ActivityDao;
import org.ccem.otus.persistence.ActivityExtractionDao;
import org.ccem.otus.persistence.ActivityProgressExtractionDao;
import org.ccem.otus.persistence.OfflineActivityDao;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;

@Stateless
public class ActivityServiceBean implements ActivityService {

  @Inject
  private ActivityDao activityDao;
  @Inject
  private ActivityAccessPermissionService activityAccessPermissionService;
  @Inject
  private ActivityProgressExtractionDao activityProgressExtractionDao;
  @Inject
  private ActivityExtractionDao activityExtractionDao;
  @Inject
  private OfflineActivityDao offlineActivityDao;
  @Inject
  private UserDao userDao;

  private boolean permissionStatus;
  private boolean userStatusHistory;
  private boolean isPresent;
  private boolean userInRestrictionList;
  private boolean acronymConfirmation;
  private boolean versionConfirmation;

  @Override
  public String create(SurveyActivity surveyActivity) {
    surveyActivity.getSurveyForm().setSurveyTemplate(null);
    ObjectId objectId = activityDao.persist(surveyActivity);
    return objectId.toString();
  }

  @Override
  public SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException {
    surveyActivity.getSurveyForm().setSurveyTemplate(null);
    return activityDao.update(surveyActivity);
  }

  @Override
  public List<SurveyActivity> list(long rn, String userEmail) {

    List<ActivityAccessPermission> activityAccessPermissions = activityAccessPermissionService.list();
    List<SurveyActivity> activities = activityDao.find(new ArrayList<>(), userEmail, rn);
    List<SurveyActivity> filteredActivities = new ArrayList<SurveyActivity>();

    activities.forEach(activity -> {
      permissionStatus = true;
      userStatusHistory = isUserInStatusHistory(activity, userEmail);

      activityAccessPermissions.forEach(permission -> {
        userInRestrictionList = permission.getExclusiveDisjunction().contains(userEmail);
        acronymConfirmation = isSameAcronym(permission, activity);
        versionConfirmation = isSameVersion(permission, activity);

        if (userInRestrictionList && !userStatusHistory && acronymConfirmation && versionConfirmation) {
          permissionStatus = false;
        }
      });

      if (permissionStatus) {
        filteredActivities.add(activity);
      }
    });
    return filteredActivities;
  }

  @Override
  public List<StageSurveyActivitiesDto> listByStageGroups(long rn, String userEmail, Map<ObjectId, String> stageMap) throws MemoryExcededException {
    List<StageSurveyActivitiesDto> activitiesDtos = activityDao.findByStageGroup(new ArrayList<>(), userEmail, rn)
      .stream().filter(stageDto -> stageDto.hasAcronyms())
      .collect(Collectors.toList());

    activitiesDtos.forEach(stageDto -> {
      String stageName = stageMap.get(stageDto.getStageID());
      stageDto.setStageName(stageName);
      stageDto.removeAcronymsWithoutActivities();
    });

    return activitiesDtos;
  }


  private boolean isSameVersion(ActivityAccessPermission permission, SurveyActivity activity) {
    return (permission.getVersion().equals(activity.getSurveyForm().getVersion()));
  }

  private boolean isSameAcronym(ActivityAccessPermission permission, SurveyActivity activity) {
    return (permission.getAcronym().equals(activity.getSurveyForm().getAcronym()));
  }

  private boolean isUserInStatusHistory(SurveyActivity activity, String userEmail) {
    List<String> finalStatus = new ArrayList<>();
    finalStatus.add(ActivityStatusOptions.FINALIZED.getName());
    finalStatus.add(ActivityStatusOptions.SAVED.getName());

    isPresent = false;
    activity.getStatusHistory().forEach(status -> {
      if (finalStatus.contains(status.getName()) && status.getUser().getEmail().equals(userEmail)) {
        isPresent = true;
      }
    });
    return isPresent;
  }

  @Override
  public SurveyActivity getByID(String id) throws DataNotFoundException {
    return activityDao.findByID(id);
  }

  @Override
  public List<SurveyActivity> get(String acronym, Integer version) throws DataNotFoundException, MemoryExcededException {
    return activityDao.getUndiscarded(acronym, version);
  }

  @Override
  public List<SurveyActivity> getExtraction(String acronym, Integer version) throws DataNotFoundException, MemoryExcededException {
    return activityDao.getExtraction(acronym, version);
  }

  @Override
  public boolean updateCheckerActivity(String checkerUpdated) throws DataNotFoundException {
    CheckerUpdatedDTO checkerUpdatedDTO = CheckerUpdatedDTO.deserialize(checkerUpdated);
    return activityDao.updateCheckerActivity(checkerUpdatedDTO);
  }

  @Override
  public SurveyActivity getActivity(String acronym, Integer version, String categoryName, Long recruitmentNumber) throws DataNotFoundException {
    return activityDao.getLastFinalizedActivity(acronym, version, categoryName, recruitmentNumber);
  }

  @Override
  public LinkedList<ActivityProgressResultExtraction> getActivityProgressExtraction(String center) throws DataNotFoundException {
    return activityProgressExtractionDao.getActivityProgressExtraction(center);
  }

  @Override
  public HashMap<Long, String> getParticipantFieldCenterByActivity(String acronym, Integer version) throws DataNotFoundException {
    return activityExtractionDao.getParticipantFieldCenter(acronym, version);
  }

  @Override
  public void save(String userEmail, OfflineActivityCollection offlineActivityCollection) throws DataNotFoundException {
    User user = userDao.fetchByEmail(userEmail);
    offlineActivityCollection.setUserId(user.get_id());
    offlineActivityDao.persist(offlineActivityCollection);
  }

  @Override
  public OfflineActivityCollectionGroupsDTO fetchOfflineActivityCollections(String userEmail) throws DataNotFoundException {
    User user = userDao.fetchByEmail(userEmail);
    return offlineActivityDao.fetchByUserId(user.get_id());
  }

  @Override
  public OfflineActivityCollection fetchOfflineActivityCollection(String collectionId) throws DataNotFoundException {
    return offlineActivityDao.fetchCollection(collectionId);
  }

  @Override
  public void deactivateOfflineActivityCollection(String offlineCollectionId, List<ObjectId> createdActivityIds) {
    offlineActivityDao.deactivateOfflineActivityCollection(offlineCollectionId, createdActivityIds);
  }

  @Override
  public boolean updateParticipantEmail(long rn, String email) {
    return activityDao.updateParticipantEmail(rn, email);
  }

  @Override
  public void removeStageFromActivities(ObjectId stageOID) {
    activityDao.removeStageFromActivities(stageOID);
  }

  @Override
  public void discardByID(ObjectId activityID) throws DataNotFoundException {
    activityDao.discardByID(activityID);
  }

}
