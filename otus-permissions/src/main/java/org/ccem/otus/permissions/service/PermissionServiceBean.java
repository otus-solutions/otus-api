package org.ccem.otus.permissions.service;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.permissions.persistence.user.UserPermissionGenericDao;

public class PermissionServiceBean implements PermissionService {

  @Inject
  private UserPermissionGenericDao userPermissionGenericDao;

  @Override
  public void getAll(String email) throws DataNotFoundException {
    userPermissionGenericDao.getUserPermissions(email);
  }
}
