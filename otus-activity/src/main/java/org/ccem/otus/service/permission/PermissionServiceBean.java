package org.ccem.otus.service.permission;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.model.survey.activity.permission.Permission;
import org.ccem.otus.persistence.PermissionDao;

@Stateless
class PermissionServiceBean implements PermissionService {

  @Inject
  private PermissionDao permissionDao;

  @Override
  public List<Permission> list() {
    return permissionDao.find();
  }
}
