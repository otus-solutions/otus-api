package org.ccem.otus.permissions.persistence.user;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;

public interface UserPermissionProfileDao {

  UserPermissionDTO getProfile(String defaultProfile) throws DataNotFoundException;

  SurveyGroupPermission getGroupPermission(String defaultProfile);
}
