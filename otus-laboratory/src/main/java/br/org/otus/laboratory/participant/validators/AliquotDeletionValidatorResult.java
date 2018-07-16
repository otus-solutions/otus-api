package br.org.otus.laboratory.participant.validators;

public class AliquotDeletionValidatorResult {
  private String examLot;
  private String transportationLot;
  private String examResult;

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

  public String getExamResult() {
    return examResult;
  }

  public void setExamResult(String examResult) {
    this.examResult = examResult;
  }

}
