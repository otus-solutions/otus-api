package org.ccem.otus.permissions.model.user;

import com.google.gson.GsonBuilder;

public class LaboratoryGroupPermission extends Permission {
  private Boolean accessPermission;

  public static String serialize(Permission permission) {
    return SurveyGroupPermission.getGsonBuilder().create().toJson(permission);
  }

  public static SurveyGroupPermission deserialize(String UserPermissionDTOJson) {
    return SurveyGroupPermission.getGsonBuilder().create().fromJson(UserPermissionDTOJson, SurveyGroupPermission.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof LaboratoryGroupPermission)) {
      return false;
    }

    LaboratoryGroupPermission c = (LaboratoryGroupPermission) o;

    return getAccessPermission() == c.getAccessPermission();
  }

  public Boolean getAccessPermission() {
    return accessPermission;
  }

}
