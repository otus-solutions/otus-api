package org.ccem.otus.permissions.model.user;

import java.util.Arrays;
import java.util.Set;

import com.google.gson.GsonBuilder;

public class SurveyGroupPermission extends Permission {
  private Set<String> groups;

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
    } else if (!(o instanceof SurveyGroupPermission)) {
      return false;
    }

    SurveyGroupPermission c = (SurveyGroupPermission) o;

    return Arrays.equals(groups.toArray(), c.groups.toArray());
  }

  public Set<String> getGroups() {
    return this.groups;
  }

}
