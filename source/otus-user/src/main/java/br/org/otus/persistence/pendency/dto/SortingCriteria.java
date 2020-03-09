package br.org.otus.persistence.pendency.dto;

import com.google.gson.annotations.SerializedName;

public class SortingCriteria {

  @SerializedName("field")
  private String fieldName;

  @SerializedName("modeValue")
  private int mode;

  public SortingCriteria(String fieldName, int modeValue) {
    this.fieldName = fieldName;
    this.mode = modeValue;
  }

  public String getFieldName() {
    return fieldName;
  }

  public int getMode() {
    return mode;
  }

  public boolean isValid(){
    return (mode == 1 || mode == -1);
  }

}
