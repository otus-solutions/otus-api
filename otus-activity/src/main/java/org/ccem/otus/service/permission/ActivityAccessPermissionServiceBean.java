package org.ccem.otus.service.permission;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
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

  @Override
  public void create(ActivityAccessPermission activityAccessPermission) throws ValidationException {
    activityAccessPermissionDao.persist(activityAccessPermission);
    
  }

  @Override
  public void update(ActivityAccessPermission activityAccessPermission) throws ValidationException {
    activityAccessPermissionDao.update(activityAccessPermission);   
  }
}
