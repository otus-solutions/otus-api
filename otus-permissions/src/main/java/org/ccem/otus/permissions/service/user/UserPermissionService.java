package org.ccem.otus.permissions.service.user;

import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;

public interface UserPermissionService {

  UserPermissionDTO getAll(String email) throws Exception;

    String savePermission(Permission permission);
}
