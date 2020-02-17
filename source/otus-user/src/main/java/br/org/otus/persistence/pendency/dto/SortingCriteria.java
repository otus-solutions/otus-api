package br.org.otus.persistence.pendency.dto;

import com.google.gson.annotations.SerializedName;

import java.util.zip.DataFormatException;

public class SortingCriteria {

  @SerializedName("field")
  private String fieldName;

  @SerializedName("modeValue")
  private int mode;

  public SortingCriteria(String fieldName, int modeValue) throws DataFormatException {
    if(modeValue != 1 && modeValue != -1){
      throw new DataFormatException("Sorting Criteria mode value invalid");
    }
    this.fieldName = fieldName;
    this.mode = modeValue;
  }

  public String getFieldName() {
    return fieldName;
  }

  public int getMode() {
    return mode;
  }

}
