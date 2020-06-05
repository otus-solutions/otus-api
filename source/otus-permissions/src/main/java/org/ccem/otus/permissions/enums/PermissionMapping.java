package org.ccem.otus.permissions.enums;

import org.ccem.otus.permissions.model.user.*;

public enum PermissionMapping {
  SURVEY_GROUP_PERMISSION(SurveyGroupPermission.class, "SurveyGroupPermission"),
  LABORATORY_PERMISSION(LaboratoryPermission.class, "LaboratoryPermission"),
  MONITORING_PERMISSION(MonitoringPermission.class, "MonitoringPermission"),
  ACTIVITY_PERMISSION(ActivityPermission.class, "ActivityPermission"),
  PARTICIPANT_PERMISSION(ParticipantPermission .class, "ParticipantPermission");

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
