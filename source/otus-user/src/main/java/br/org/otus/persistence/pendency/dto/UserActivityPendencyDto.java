package br.org.otus.persistence.pendency.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.ccem.otus.exceptions.Dto;

public class UserActivityPendencyDto implements Dto { //TODO pq implementar Dto?

  @SerializedName(value = "currentQuantity")
  private int currentQuantity;

  @SerializedName(value = "quantityToGet")
  private int quantityToGet;

  @SerializedName(value = "order")
  private SortingCriteria[] sortingCriteria;

  @SerializedName(value = "filter")
  private UserActivityPendencyRequestFilterDto filterDto;

  public int getCurrentQuantity() {
    return currentQuantity;
  }

  public int getQuantityToGet() { return quantityToGet; }

  public SortingCriteria[] getSortingCriteria() {
    return sortingCriteria;
  }

  public UserActivityPendencyRequestFilterDto getFilterDto() {
    return filterDto;
  }

  @Override
  public Boolean isValid() {
    return Boolean.TRUE;
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
}