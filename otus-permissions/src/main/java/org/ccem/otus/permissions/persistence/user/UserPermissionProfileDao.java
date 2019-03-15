package org.ccem.otus.permissions.persistence.user;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface UserPermissionProfileDao {

  UserPermissionDTO getProfile(String defaultProfile) throws DataNotFoundException;

}
