package org.ccem.otus.permissions.model.user;

import com.google.gson.GsonBuilder;

public class MonitoringPermission extends Permission {
  private Boolean centerActivitiesAccess;
  private Boolean activityFlagsAccess;
  private Boolean laboratoryFlagsAccess;
  private Boolean laboratoryControlAccess;
  private Boolean pendencyVisualizerAccess;

  public static String serialize(Permission permission) {
    return MonitoringPermission.getGsonBuilder().create().toJson(permission);
  }

  public static MonitoringPermission deserialize(String UserPermissionDTOJson) {
    return MonitoringPermission.getGsonBuilder().create().fromJson(UserPermissionDTOJson, MonitoringPermission.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof MonitoringPermission)) {
      return false;
    }

    MonitoringPermission c = (MonitoringPermission) o;

    return getActivityFlagsAccess() == c.getActivityFlagsAccess() &&
      getCenterActivitiesAccess() == c.getCenterActivitiesAccess() &&
      getLaboratoryControlAccess() == c.getLaboratoryControlAccess() &&
      getPendencyVisualizerAccess() == c.getPendencyVisualizerAccess() &&
      getLaboratoryFlagsAccess() == c.getLaboratoryFlagsAccess();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result +
      ((centerActivitiesAccess == null) ? 0 : centerActivitiesAccess.hashCode()) +
      ((activityFlagsAccess == null) ? 0 : activityFlagsAccess.hashCode()) +
      ((laboratoryFlagsAccess == null) ? 0 : laboratoryFlagsAccess.hashCode()) +
      ((laboratoryControlAccess == null) ? 0 : laboratoryControlAccess.hashCode()) +
      ((pendencyVisualizerAccess == null) ? 0 : pendencyVisualizerAccess.hashCode());
    return result;
  }

  public Boolean getCenterActivitiesAccess() {
    return centerActivitiesAccess;
  }

  public Boolean getActivityFlagsAccess() {
    return activityFlagsAccess;
  }

  public Boolean getLaboratoryFlagsAccess() {
    return laboratoryFlagsAccess;
  }

  public Boolean getLaboratoryControlAccess() {
    return laboratoryControlAccess;
  }

  public Boolean getPendencyVisualizerAccess() {
    return pendencyVisualizerAccess;
  }
}
