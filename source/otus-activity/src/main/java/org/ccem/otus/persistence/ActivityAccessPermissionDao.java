package org.ccem.otus.persistence;

import java.util.List;

import org.ccem.otus.model.survey.activity.permission.ActivityAccessPermission;

public interface ActivityAccessPermissionDao {

  List<ActivityAccessPermission> find();

  void persist(ActivityAccessPermission activityAccessPermission);

  void update(ActivityAccessPermission activityAccessPermission);
}
