package br.org.otus.persistence.pendency.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.ccem.otus.exceptions.Dto;

import java.util.zip.DataFormatException;

public class UserActivityPendencyDto implements Dto {

  @SerializedName(value = "currentQuantity")
  private int currentQuantity;

  @SerializedName(value = "quantityToGet")
  private int quantityToGet;

  @SerializedName(value = "order")
  private String[] fieldsToOrder;

  @SerializedName(value = "orderMode")
  private Integer orderMode;

  @SerializedName(value = "filter")
  private UserActivityPendencyFilterDto filterDto;

  public int getCurrentQuantity() { return currentQuantity; }

  public int getQuantityToGet() { return quantityToGet; }

  public String[] getFieldsToOrder() { return fieldsToOrder; }

  public Integer getOrderMode() { return orderMode; }

  public SortingCriteria[] getSortingCriteria() throws DataFormatException {
    if(fieldsToOrder==null){
      return null;
    }
    int n = fieldsToOrder.length;
    SortingCriteria[] sortingCriteria = new SortingCriteria[n];
    for (int i = 0; i < n; i++) {
      sortingCriteria[i] = new SortingCriteria(fieldsToOrder[i], orderMode);
    }
    return sortingCriteria;
  }

  public UserActivityPendencyFilterDto getFilterDto() {
    return filterDto;
  }

  public static String serialize(UserActivityPendencyDto userActivityPendencyDto) {
    return getGsonBuilder().create().toJson(userActivityPendencyDto);
  }

  public static UserActivityPendencyDto deserialize(String orderRequestJson) {
    return UserActivityPendencyDto.getGsonBuilder().create().fromJson(orderRequestJson, UserActivityPendencyDto.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

  @Override
  public Boolean isValid() {
    return
      (filterDto==null || filterDto.isValid()) &&
      (
        (fieldsToOrder==null && orderMode==null) ||
        (fieldsToOrder!=null && fieldsToOrder.length > 0 && orderMode!=null && (orderMode==1 || orderMode==-1))
      );
  }
}