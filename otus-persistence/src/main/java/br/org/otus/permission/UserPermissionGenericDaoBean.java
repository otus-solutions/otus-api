package br.org.otus.permission;

import javax.inject.Inject;

import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionDao;
import org.ccem.otus.permissions.persistence.user.UserPermissionGenericDao;
import org.ccem.otus.permissions.persistence.user.UserPermissionProfileDao;

public class UserPermissionGenericDaoBean implements UserPermissionGenericDao {

  private static final String DEFAULT_PROFILE = "DEFAULT";

  @Inject
  private UserPermissionDao userPermissionDao;

  @Inject
  private UserPermissionProfileDao userPermissionProfileDao;

  @Override
  public UserPermissionDTO getUserPermissions(String email) throws Exception {
    UserPermissionDTO userCustomPermission = userPermissionDao.getAll(email);
    UserPermissionDTO permissionProfile = userPermissionProfileDao.getProfile(DEFAULT_PROFILE);
    
    userCustomPermission.concatenatePermissions(permissionProfile);
    
    return null;
  }

}
