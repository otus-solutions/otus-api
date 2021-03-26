package br.org.otus.laboratory.participant.validators;

public class AliquotDeletionValidatorResponse {
  private String examLot;
  private String transportationLot;
  private Boolean examResult;
  private Boolean deletionValidated;
  private Boolean receivedMaterial;

  public AliquotDeletionValidatorResponse() {
    this.deletionValidated = Boolean.TRUE;
  }

  public String getExamLot() {
    return examLot;
  }

  public void setExamLot(String examLot) {
    this.examLot = examLot;
  }

  public String getTransportationLot() {
    return transportationLot;
  }

  public void setTransportationLot(String transportationLot) {
    this.transportationLot = transportationLot;
  }

  public Boolean getExamResult() {
    return examResult;
  }

  public void setExamResult(Boolean examResult) {
    this.examResult = examResult;
  }

  public Boolean isDeletionValidated() {
    return deletionValidated;
  }

  public void setDeletionValidated(Boolean deletionValidated) {
    this.deletionValidated = deletionValidated;
  }

  public void setReceivedMaterial(Boolean receivedMaterial) {
    this.receivedMaterial = receivedMaterial;
  }

  public void getReceivedMaterial() {
    return this.receivedMaterial;
  }

}
