package br.org.otus.persistence.pendency.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.ccem.otus.exceptions.Dto;

import javax.inject.Inject;

public class UserActivityPendencyDto implements Dto {

  @SerializedName(value = "currentQuantity")
  private int currentQuantity;

  @SerializedName(value = "quantityToGet")
  private int quantityToGet;

  @Inject
  @SerializedName(value = "order")
  private UserActivityPendencyOrderDto orderDto;

  @Inject
  @SerializedName(value = "filter")
  private UserActivityPendencyFilterDto filterDto;

  public int getCurrentQuantity() { return currentQuantity; }

  public int getQuantityToGet() { return quantityToGet; }

  public UserActivityPendencyOrderDto getOrderDto() { return orderDto; }

  public UserActivityPendencyFilterDto getFilterDto() {
    return filterDto;
  }

  public static String serialize(UserActivityPendencyDto userActivityPendencyDto) {
    return getGsonBuilder().create().toJson(userActivityPendencyDto);
  }

  public static UserActivityPendencyDto deserialize(String userActivityPendencyDtoJson) {
    return UserActivityPendencyDto.getGsonBuilder().create().fromJson(userActivityPendencyDtoJson, UserActivityPendencyDto.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

  @Override
  public Boolean isValid() {
    boolean validFilter = (filterDto == null);
    if(filterDto != null){
      validFilter = filterDto.isValid();
    }

    boolean validOrder = (orderDto == null);
    if(orderDto != null){
      validOrder = orderDto.isValid();
    }

    return (validFilter && validOrder);
  }
}