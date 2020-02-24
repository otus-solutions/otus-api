package br.org.otus.laboratory.unattached.DTOs;

public class ValidationErrorResponseDTO {
  private String participantFieldCenter;
  private String laboratoryFieldCenter;
  private String participantCollectGroup;
  private String laboratoryCollectGroup;

  public ValidationErrorResponseDTO(String participantFieldCenter, String laboratoryFieldCenter, String participantCollectGroup, String laboratoryCollectGroup) {
    this.participantFieldCenter = participantFieldCenter;
    this.laboratoryFieldCenter = laboratoryFieldCenter;
    this.participantCollectGroup = participantCollectGroup;
    this.laboratoryCollectGroup = laboratoryCollectGroup;
  }

  public String getParticipantFieldCenter() {
    return participantFieldCenter;
  }

  public String getLaboratoryFieldCenter() {
    return laboratoryFieldCenter;
  }

  public String getParticipantCollectGroup() {
    return participantCollectGroup;
  }

  public String getLaboratoryCollectGroup() {
    return laboratoryCollectGroup;
  }
}
