package org.ccem.otus.model.searchSettingsDto;

import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.model.SerializableModel;

public class OrderSettingsDto extends SerializableModel implements Dto {

  private Enum fieldOptions;
  private String[] fields;
  private Integer mode;

  public OrderSettingsDto(Enum fieldOptions) {
    this.fieldOptions = fieldOptions;
  }

  public String[] getFields() { return fields; }

  public Integer getMode() { return mode; }

  public Boolean isValid() {
    if(fields == null){
      return (mode == null);
    }
    if(mode == null || (mode != 1 && mode != -1)){
      return false;
    }
    boolean fieldsAreValid = (fields.length > 0);
    for(String field : fields){
      fieldsAreValid &= (fieldOptions.valueOf(fieldOptions.getClass(), field) != null);
    }
    return fieldsAreValid;
  }

  public static OrderSettingsDto deserialize(String json){
    return (OrderSettingsDto)deserialize(json, OrderSettingsDto.class);
  }
}
