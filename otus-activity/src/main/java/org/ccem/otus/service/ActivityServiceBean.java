package org.ccem.otus.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.model.survey.activity.dto.CheckerUpdatedDTO;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.persistence.ActivityDao;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;

@Stateless
public class ActivityServiceBean implements ActivityService {

  @Inject
  private ActivityDao activityDao;
  @Inject
  private ActivityAccessPermissionService activityAccessPermissionService;

  private boolean permissionStatus;
  private boolean userStatusHistory;
  private boolean isPresent;
  private boolean userInRestrictionList;
  private boolean acronymConfirmation;
  private boolean versionConfirmation;

  @Override
  public String create(SurveyActivity surveyActivity) {
    ObjectId objectId = activityDao.persist(surveyActivity);
    return objectId.toString();
  }

  @Override
  public SurveyActivity update(SurveyActivity surveyActivity) throws DataNotFoundException {
    return activityDao.update(surveyActivity);
  }

  @Override
  public List<SurveyActivity> list(long rn, String userEmail) {

    List<ActivityAccessPermission> activityAccessPermissions = activityAccessPermissionService.list();
    List<SurveyActivity> activities = getPermittedSurveys(userEmail, rn);
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

  private List<SurveyActivity> getPermittedSurveys(String userEmail, Long rn) {
    return activityDao.find(new ArrayList<>(), userEmail, rn);
  }

  private boolean isSameVersion(ActivityAccessPermission permission, SurveyActivity activity) {
    return (permission.getVersion().equals(activity.getSurveyForm().getVersion()));
  }

  private boolean isSameAcronym(ActivityAccessPermission permission, SurveyActivity activity) {
    return (permission.getAcronym().equals(activity.getSurveyForm().getSurveyTemplate().identity.acronym));
  }

  private boolean isUserInStatusHistory(SurveyActivity activity, String userEmail) {
    isPresent = false;
    activity.getStatusHistory().forEach(status -> {
      if (status.getUser().getEmail().equals(userEmail)) {
        isPresent = true;
      }
    });
    return isPresent;
  }

  private boolean isUsersPermissionInStatusHistory(ActivityAccessPermission permission, SurveyActivity activity) {
    isPresent = false;
    activity.getStatusHistory().forEach(status -> {
      permission.getExclusiveDisjunction().forEach(otherUserEmail -> {
        if (status.getUser().getEmail().equals(otherUserEmail)) {
          isPresent = true;
        }
      });
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
  public boolean updateCheckerActivity(String checkerUpdated) throws DataNotFoundException {
    CheckerUpdatedDTO checkerUpdatedDTO = CheckerUpdatedDTO.deserialize(checkerUpdated);
    return activityDao.updateCheckerActivity(checkerUpdatedDTO);
  }

}
