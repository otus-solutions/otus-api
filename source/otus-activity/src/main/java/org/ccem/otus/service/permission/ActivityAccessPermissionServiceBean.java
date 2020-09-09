package org.ccem.otus.service.permission;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.persistence.ActivityAccessPermissionDao;

@Stateless
public class ActivityAccessPermissionServiceBean implements ActivityAccessPermissionService {

  @Inject
  private ActivityAccessPermissionDao activityAccessPermissionDao;

  @Override
  public void create(ActivityAccessPermission activityAccessPermission) {
    activityAccessPermissionDao.persist(activityAccessPermission);
  }

  @Override
  public void update(ActivityAccessPermission activityAccessPermission) {
    activityAccessPermissionDao.update(activityAccessPermission);
  }

  @Override
  public List<ActivityAccessPermission> list() {
    return activityAccessPermissionDao.find();
  }

  @Override
  public ActivityAccessPermission get(String acronym, Integer version) throws DataNotFoundException {
    return activityAccessPermissionDao.get(acronym, version);
  }

}