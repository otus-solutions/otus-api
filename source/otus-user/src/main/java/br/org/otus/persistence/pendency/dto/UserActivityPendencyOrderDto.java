package br.org.otus.persistence.pendency.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.ccem.otus.exceptions.Dto;

import java.util.zip.DataFormatException;

public class UserActivityPendencyOrderDto implements Dto {

  @SerializedName(value = "fields")
  private String[] fieldsToOrder;

  @SerializedName(value = "mode")
  private Integer mode;

  public String[] getFieldsToOrder() { return fieldsToOrder; }

  public Integer getOrderMode() { return mode; }

  public SortingCriteria[] getSortingCriteria() throws DataFormatException {
    if(fieldsToOrder==null){
      return null;
    }
    int n = fieldsToOrder.length;
    SortingCriteria[] sortingCriteria = new SortingCriteria[n];
    for (int i = 0; i < n; i++) {
      sortingCriteria[i] = new SortingCriteria(fieldsToOrder[i], mode);
    }
    return sortingCriteria;
  }

  public static String serialize(UserActivityPendencyOrderDto userActivityPendencyOrderDto) {
    return getGsonBuilder().create().toJson(userActivityPendencyOrderDto);
  }

  public static UserActivityPendencyOrderDto deserialize(String orderRequestJson) {
    return UserActivityPendencyOrderDto.getGsonBuilder().create().fromJson(orderRequestJson, UserActivityPendencyOrderDto.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

  @Override
  public Boolean isValid() {
    if(fieldsToOrder == null){
      return (mode == null);
    }
    if(mode == null || (mode != 1 && mode != -1)){
      return false;
    }
    boolean fieldsAreValid = (fieldsToOrder.length > 0);
    for(String field : fieldsToOrder){
      fieldsAreValid &= UserActivityPendencyFieldFilterOptions.contains(field);
    }
    return fieldsAreValid;
  }
}
