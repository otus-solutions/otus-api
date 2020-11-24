package org.ccem.otus.permissions.model.user;

import com.google.gson.GsonBuilder;

public class LaboratoryPermission extends Permission {
  private Boolean participantLaboratoryAccess;
  private Boolean sampleTransportationAccess;
  private Boolean examLotsAccess;
  private Boolean examSendingAccess;
  private Boolean unattachedLaboratoriesAccess;
  private Boolean laboratoryMaterialManagerAccess;
  private Boolean aliquotManagerAccess;

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

    return getExamLotsAccess() == c.getExamLotsAccess() &&
      getParticipantLaboratoryAccess() == c.getParticipantLaboratoryAccess() &&
      getExamSendingAccess() == c.getExamSendingAccess() &&
      getSampleTransportationAccess() == c.getSampleTransportationAccess() &&
      getUnattachedLaboratoriesAccess() == c.getUnattachedLaboratoriesAccess() &&
      getLaboratoryMaterialManagerAccess() == c.getLaboratoryMaterialManagerAccess() &&
      getAliquotManagerAccess() == c.getAliquotManagerAccess();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result +
      ((participantLaboratoryAccess == null) ? 0 : participantLaboratoryAccess.hashCode()) +
      ((sampleTransportationAccess == null) ? 0 : sampleTransportationAccess.hashCode()) +
      ((examLotsAccess == null) ? 0 : examLotsAccess.hashCode()) +
      ((examSendingAccess == null) ? 0 : examSendingAccess.hashCode()) +
      ((unattachedLaboratoriesAccess == null) ? 0 : unattachedLaboratoriesAccess.hashCode()) +
      ((laboratoryMaterialManagerAccess == null) ? 0 : laboratoryMaterialManagerAccess.hashCode()) +
      ((aliquotManagerAccess == null) ? 0 : aliquotManagerAccess.hashCode());
    return result;
  }

  public Boolean getSampleTransportationAccess() {
    return sampleTransportationAccess;
  }

  public Boolean getExamLotsAccess() {
    return examLotsAccess;
  }

  public Boolean getExamSendingAccess() {
    return examSendingAccess;
  }

  public Boolean getUnattachedLaboratoriesAccess() {
    return unattachedLaboratoriesAccess;
  }

  public Boolean getParticipantLaboratoryAccess() {
    return participantLaboratoryAccess;
  }

  public Boolean getLaboratoryMaterialManagerAccess() {
    return laboratoryMaterialManagerAccess;
  }

  public Boolean getAliquotManagerAccess() {
    return aliquotManagerAccess;
  }
}
