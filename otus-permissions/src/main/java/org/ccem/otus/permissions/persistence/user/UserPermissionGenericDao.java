package org.ccem.otus.permissions.persistence.user;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.Permission;

import java.util.ArrayList;
import java.util.List;

public interface UserPermissionGenericDao {

  UserPermissionDTO getUserPermissions(String email) throws Exception;

  Permission savePermission(Permission permission) throws DataNotFoundException;

  List<String> getUserPermittedSurveys(String userEmail);
}
