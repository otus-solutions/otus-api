package org.ccem.otus.permissions.persistence.user;

import org.ccem.otus.permissions.model.user.Permission;

public interface UserPermissionDao {

  UserPermissionDTO getAll(String email);

    void savePermission(Permission permission);
}
