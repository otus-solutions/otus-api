package br.org.otus.survey.activity.permission;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;

public class ActivityAccessPermissionFacade {
  
  @Inject
  private ActivityAccessPermissionService activityAccessPermissionService;
  
  public void create(ActivityAccessPermission permission) {    
      activityAccessPermissionService.create(permission);  
  }
  
  public List<ActivityAccessPermission> getAll() {
    return activityAccessPermissionService.list();
  }
  
  public void update(ActivityAccessPermission permission) {    
      activityAccessPermissionService.update(permission);
  }  
}
