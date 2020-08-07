package org.ccem.otus.permissions.model.user;

import com.google.gson.GsonBuilder;

public class ParticipantPermission extends Permission {
  private Boolean participantListAccess;
  private Boolean participantCreateAccess;
  private Boolean anonymousParticipantAccess;

  public static String serialize(Permission permission) {
    return ParticipantPermission.getGsonBuilder().create().toJson(permission);
  }

  public static ParticipantPermission deserialize(String UserPermissionDTOJson) {
    return ParticipantPermission.getGsonBuilder().create().fromJson(UserPermissionDTOJson, ParticipantPermission.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof ParticipantPermission)) {
      return false;
    }

    ParticipantPermission c = (ParticipantPermission) o;

    return getAnonymousParticipantAccess() == c.getAnonymousParticipantAccess() &&
      getParticipantCreateAccess() == c.getParticipantCreateAccess() &&
      getParticipantListAccess() == c.getParticipantListAccess();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result +
      ((participantListAccess == null) ? 0 : participantListAccess.hashCode()) +
      ((participantCreateAccess == null) ? 0 : participantCreateAccess.hashCode()) +
      ((anonymousParticipantAccess == null) ? 0 : anonymousParticipantAccess.hashCode());
    return result;
  }

  public Boolean getParticipantListAccess() {
    return participantListAccess;
  }

  public Boolean getParticipantCreateAccess() {
    return participantCreateAccess;
  }

  public Boolean getAnonymousParticipantAccess() {
    return anonymousParticipantAccess;
  }
}
