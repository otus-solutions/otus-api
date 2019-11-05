package org.ccem.otus.permissions.persistence.user;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.Permission;

public interface UserPermissionGenericDao {

  UserPermissionDTO getUserPermissions(String email) throws Exception;

  Permission savePermission(Permission permission) throws DataNotFoundException;

  List<String> getUserPermittedSurveys(String userEmail);

  void removeFromPermissions(String surveyGroupName) throws DataNotFoundException;
}
