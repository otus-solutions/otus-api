package org.ccem.otus.service.permission;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;

public interface ActivityAccessPermissionService {

  void create(ActivityAccessPermission activityAccessPermissionDto);

  void update(ActivityAccessPermission activityAccessPermissionDto);

  List<ActivityAccessPermission> list();

  ActivityAccessPermission get(String acronym, Integer version) throws DataNotFoundException;

}
