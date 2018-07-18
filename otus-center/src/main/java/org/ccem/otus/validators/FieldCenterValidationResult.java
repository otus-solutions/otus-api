package org.ccem.otus.validators;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.model.FieldCenter;

import com.google.gson.Gson;

public class FieldCenterValidationResult {

  private boolean isValid = true;
  private ArrayList<String> conflits = new ArrayList<>();

  public void pushConflict(String value) {
    this.conflits.add(value);
  }

  public ArrayList<String> getValue() {
    return conflits;
  }

  public void setValid(boolean valid) {
    isValid = valid;
  }

  public boolean isValid() {
    return isValid;
  }

}
