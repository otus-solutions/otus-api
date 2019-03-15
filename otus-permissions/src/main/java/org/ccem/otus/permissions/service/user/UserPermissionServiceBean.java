package org.ccem.otus.permissions.service.user;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionGenericDao;

@Stateless
public class UserPermissionServiceBean implements UserPermissionService {

  @Inject
  private UserPermissionGenericDao userPermissionGenericDao;

  @Override
  public UserPermissionDTO getAll(String email) throws Exception {
    return userPermissionGenericDao.getUserPermissions(email);
  }

  @Override
  public String savePermission(Permission permission) {
    return userPermissionGenericDao.savePermission(permission);
  }
}
