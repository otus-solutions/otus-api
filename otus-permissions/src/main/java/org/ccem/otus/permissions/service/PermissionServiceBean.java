package org.ccem.otus.permissions.service;

import org.ccem.otus.permissions.persistence.user.UserPermissionsDao;

import javax.inject.Inject;

public class PermissionServiceBean implements PermissionService {

    @Inject
    private UserPermissionsDao userPermissionsDao;
}
