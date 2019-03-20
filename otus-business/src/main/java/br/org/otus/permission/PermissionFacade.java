package br.org.otus.permission;

import javax.inject.Inject;

import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.persistence.user.UserPermissionDTO;
import org.ccem.otus.permissions.service.user.UserPermissionService;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class PermissionFacade {

  @Inject
  private UserPermissionService userPermissionService;

  public UserPermissionDTO getAll(String email) {
    try {
      return userPermissionService.getAll(email);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public Permission savePermission(UserPermissionDTO userPermissionDTO) {
    try {
      Permission permission = userPermissionDTO.getPermissions().get(0);
      return userPermissionService.savePermission(permission);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

}
