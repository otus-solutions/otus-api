package br.org.otus.survey.activity.permission;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;
import org.ccem.otus.service.permission.ActivityAccessPermissionService;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;



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