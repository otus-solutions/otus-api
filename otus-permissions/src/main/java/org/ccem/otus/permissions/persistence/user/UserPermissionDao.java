package org.ccem.otus.permissions.persistence.user;

public interface UserPermissionDao {

  public UserPermissionDTO getAll(String email);

}
