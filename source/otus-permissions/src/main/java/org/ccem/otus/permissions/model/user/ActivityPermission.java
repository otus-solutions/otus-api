package org.ccem.otus.permissions.model.user;

import com.google.gson.GsonBuilder;

public class ActivityPermission extends Permission {
  private Boolean participantActivityAccess;
  private Boolean offlineActivitySincAccess;

  public static String serialize(Permission permission) {
    return ActivityPermission.getGsonBuilder().create().toJson(permission);
  }

  public static ActivityPermission deserialize(String UserPermissionDTOJson) {
    return ActivityPermission.getGsonBuilder().create().fromJson(UserPermissionDTOJson, ActivityPermission.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof ActivityPermission)) {
      return false;
    }

    ActivityPermission c = (ActivityPermission) o;

    return getParticipantActivityAccess() == c.getParticipantActivityAccess() && getOfflineActivitySincAccess() == c.getOfflineActivitySincAccess();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result +
      ((participantActivityAccess == null) ? 0 : participantActivityAccess.hashCode()) +
      ((offlineActivitySincAccess == null) ? 0 : offlineActivitySincAccess.hashCode());
    return result;
  }

  public Boolean getParticipantActivityAccess() {
    return this.participantActivityAccess;
  }
  public Boolean getOfflineActivitySincAccess() {
    return this.offlineActivitySincAccess;
  }
}
