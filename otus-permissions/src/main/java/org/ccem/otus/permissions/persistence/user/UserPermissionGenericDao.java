package org.ccem.otus.permissions.persistence.user;

import org.ccem.otus.permissions.model.user.Permission;

public interface UserPermissionGenericDao {

  UserPermissionDTO getUserPermissions(String email) throws Exception;

    String savePermission(Permission permission);
}
