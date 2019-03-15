package br.org.otus.permission;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.permissions.service.PermissionService;

public class PermissionFacade {

  @Inject
  private PermissionService permissionService;

  public List<String> getAll(String email) {
    // permissionService.getAll(email);
    return null;
  }

  public String savePermission(String permissionJson) {
    return null;
  }

}
