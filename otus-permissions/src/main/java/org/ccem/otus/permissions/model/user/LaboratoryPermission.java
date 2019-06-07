package org.ccem.otus.permissions.model.user;

import com.google.gson.GsonBuilder;

public class LaboratoryPermission extends Permission {
  private Boolean access;

  public static String serialize(Permission permission) {
    return LaboratoryPermission.getGsonBuilder().create().toJson(permission);
  }

  public static LaboratoryPermission deserialize(String UserPermissionDTOJson) {
    return LaboratoryPermission.getGsonBuilder().create().fromJson(UserPermissionDTOJson, LaboratoryPermission.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof LaboratoryPermission)) {
      return false;
    }

    LaboratoryPermission c = (LaboratoryPermission) o;

    return getAccess() == c.getAccess();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((access == null) ? 0 : access.hashCode());
    return result;
  }

  public Boolean getAccess() {
    return access;
  }

}
