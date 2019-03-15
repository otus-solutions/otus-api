package org.ccem.otus.permissions.persistence.user;

public interface UserPermissionGenericDao {

  UserPermissionDTO getUserPermissions(String email) throws Exception;

}
