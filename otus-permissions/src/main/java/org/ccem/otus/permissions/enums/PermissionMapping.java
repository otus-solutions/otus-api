package org.ccem.otus.permissions.enums;

import org.ccem.otus.permissions.model.user.LaboratoryGroupPermission;
import org.ccem.otus.permissions.model.user.Permission;
import org.ccem.otus.permissions.model.user.SurveyGroupPermission;

public enum PermissionMapping {
  SURVEY_GROUP_PERMISSION(SurveyGroupPermission.class, "SurveyGroupPermission"),
  LABORATORY_GROUP_PERMISSION(LaboratoryGroupPermission.class, "LaboratoryGroupPermission");

  private Class<? extends Permission> permission;
  private String Key;

  PermissionMapping(Class<? extends Permission> permission, String Key) {
    this.permission = permission;
    this.Key = Key;
  }

  public Class<? extends Permission> getItemClass() {
    return this.permission;
  }

  public String getPermissionKey() {
    return this.Key;
  }

  public static PermissionMapping getEnumByObjectType(String objectType) {
    PermissionMapping aux = null;
    PermissionMapping[] var2 = values();

    for (PermissionMapping item : var2) {
      if (item.getPermissionKey().equals(objectType)) {
        aux = item;
      }
    }

    if (aux == null) {
      throw new RuntimeException("Error: " + objectType + " was not found at PermissionMapping.");
    } else {
      return aux;
    }
  }

}
