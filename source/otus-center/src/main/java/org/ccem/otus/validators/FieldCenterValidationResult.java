package org.ccem.otus.validators;

import java.util.ArrayList;
import java.util.List;

public class FieldCenterValidationResult {

  private Boolean isValid;
  private List<String> conflicts;

  public FieldCenterValidationResult() {
    isValid = true;
    conflicts = new ArrayList<>();
  }

  public void pushConflict(String fieldExistent) {
    this.conflicts.add(fieldExistent);
  }

  public List<String> getConflicts() {
    return conflicts;
  }

  public void setValid(Boolean isValid) {
    this.isValid = isValid;
  }

  public Boolean isValid() {
    return isValid;
  }

}
