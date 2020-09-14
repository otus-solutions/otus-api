package org.ccem.otus.persistence;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;

public interface ActivityAccessPermissionDao {

  void persist(ActivityAccessPermission activityAccessPermission);

  void update(ActivityAccessPermission activityAccessPermission);

  List<ActivityAccessPermission> find();

  ActivityAccessPermission get(String acronym, Integer version) throws DataNotFoundException;
}
