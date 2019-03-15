package org.ccem.otus.permissions.persistence.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ccem.otus.permissions.model.Permission;
import org.ccem.otus.permissions.utils.PermissionAdapter;

import com.google.gson.GsonBuilder;

public class UserPermissionDTO {

  private ArrayList<Permission> permissions;

  public void concatenatePermissions(UserPermissionDTO userPermissionDTO) {
    int length = userPermissionDTO.getPermissions().size();

    for(int i = 0; i < question.length; i++) {}
    this.permissions.forEach(permission -> {
      List<Permission> filtered = userPermissionDTO.getPermissions().stream().filter(userPermission -> permission.getObjectType().equals(userPermission.getObjectType())).collect(Collectors.toList());
//      if (!filtered.isEmpty()) {
//        permission = filtered.get(0);
//      }
    });
  }

  public static UserPermissionDTO deserialize(String reportTemplateJson) {
    UserPermissionDTO userPermissionsDTO = UserPermissionDTO.getGsonBuilder().create().fromJson(reportTemplateJson, UserPermissionDTO.class);
    return userPermissionsDTO;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Permission.class, new PermissionAdapter());
    builder.disableHtmlEscaping();
    return builder;
  }

  public ArrayList<Permission> getPermissions() {
    return permissions;
  }

}
