package org.ccem.otus.permissions.service.user;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.persistence.user.UserPermissionGenericDao;

import java.util.List;

@Stateless
public class UserPermissionServiceBean implements UserPermissionService {

  @Inject
  private UserPermissionGenericDao userPermissionGenericDao;

  @Override
  public UserPermissionDTO getAll(String email) throws Exception {
    return userPermissionGenericDao.getUserPermissions(email);
  }

  @Override
  public Permission savePermission(Permission permission) throws DataNotFoundException {
    return userPermissionGenericDao.savePermission(permission);
  }

  @Override
  public List<String> getUserPermittedSurveys(String userEmail) {
    return userPermissionGenericDao.getUserPermittedSurveys(userEmail);
  }
}
