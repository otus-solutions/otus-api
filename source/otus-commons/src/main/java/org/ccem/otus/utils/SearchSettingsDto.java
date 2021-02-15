package org.ccem.otus.utils;

import com.google.gson.annotations.SerializedName;
import org.ccem.otus.model.SerializableModel;

public abstract class SearchSettingsDto extends SerializableModel {

  @SerializedName(value = "currentQuantity")
  protected int currentQuantity;

  @SerializedName(value = "quantityToGet")
  protected int quantityToGet;

  @SerializedName(value = "fields")
  protected String[] fieldsToOrder;

  @SerializedName(value = "mode")
  private Integer mode;

  public int getCurrentQuantity() {
    return currentQuantity;
  }

  public int getQuantityToGet() {
    return quantityToGet;
  }

  public String[] getFieldsToOrder() {
    return fieldsToOrder;
  }

  public Integer getMode() {
    return mode;
  }

  public Boolean isValid() {
    if(fieldsToOrder == null){
      return (mode == null);
    }
    if(mode == null || (mode != 1 && mode != -1)){
      return false;
    }
    boolean fieldsAreValid = (fieldsToOrder.length > 0);
    for(String field : fieldsToOrder){
      fieldsAreValid &= isValidField(field);
    }
    return fieldsAreValid;
  }

  protected abstract boolean isValidField(String fieldName);  //TODO Ex: UserActivityPendencyFieldOrderingOptions.contains
}
