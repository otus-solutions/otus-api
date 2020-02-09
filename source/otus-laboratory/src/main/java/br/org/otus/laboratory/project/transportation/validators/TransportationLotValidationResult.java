package br.org.otus.laboratory.project.transportation.validators;

import java.util.ArrayList;

public class TransportationLotValidationResult {

  private boolean isValid = true;
  private ArrayList<String> value = new ArrayList<>();
  private String errorCode;

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public boolean isValid() {
    return isValid;
  }

  public ArrayList<String> getValue() {
    return value;
  }

  public void setValid(boolean valid) {

    isValid = valid;
  }

  public void setValue(ArrayList<String> value) {
    this.value = value;
  }

  public void pushConflict(String aliquotCode) {
    this.value.add(aliquotCode);
  }

  public TransportationLotValidationResult() {
  }
}
