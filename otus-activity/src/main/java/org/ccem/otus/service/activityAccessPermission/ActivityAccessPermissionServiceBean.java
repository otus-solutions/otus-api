package org.ccem.otus.service.activityAccessPermission;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.persistence.ActivityAccessPermissionDao;

@Stateless
class ActivityAccessPermissionServiceBean implements ActivityAccessPermissionService {

  @Inject
  private ActivityAccessPermissionDao activityAccessPermissionDao;

  @Override
  public List<ActivityAccessPermission> list() {
    return activityAccessPermissionDao.find();
  }
}
