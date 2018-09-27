package org.ccem.otus.service.permission;

import java.util.List;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;

public interface ActivityAccessPermissionService {
  
  void create(ActivityAccessPermission activityAccessPermissionDto) throws ValidationException;

  void update(ActivityAccessPermission activityAccessPermissionDto) throws ValidationException;
  
  List<ActivityAccessPermission> list();

}
