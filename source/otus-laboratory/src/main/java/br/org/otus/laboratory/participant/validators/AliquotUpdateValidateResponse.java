package br.org.otus.laboratory.participant.validators;

import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;

import java.util.ArrayList;
import java.util.List;

public class AliquotUpdateValidateResponse {

  private static final String NAME = "AliquotUpdateValidateResponse";
  private List<SimpleAliquot> conflicts;
  private List<String> tubesNotFound;

  public AliquotUpdateValidateResponse() {
    this.conflicts = new ArrayList<SimpleAliquot>();
    this.tubesNotFound = new ArrayList<String>();
  }

  public List<SimpleAliquot> getConflicts() {
    return conflicts;
  }

  public void setConflicts(List<SimpleAliquot> conflicts) {
    this.conflicts = conflicts;
  }

  public List<String> getTubesNotFound() {
    return tubesNotFound;
  }

  public void setTubesNotFound(List<String> tubesNotFound) {
    this.tubesNotFound = tubesNotFound;
  }

  public boolean isValid() {
    return conflicts.isEmpty() && tubesNotFound.isEmpty();
  }
}
