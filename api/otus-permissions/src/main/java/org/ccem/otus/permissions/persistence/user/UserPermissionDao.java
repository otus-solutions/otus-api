package org.ccem.otus.permissions.persistence.user;

import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;

public interface UserPermissionDao {
  UserPermissionDTO getAll(String email);

  void savePermission(Permission permission);

  void deletePermission(Permission permission);

  SurveyGroupPermission getGroupPermission(String email);

  void removeFromPermissions(String surveyGroupName);
}
