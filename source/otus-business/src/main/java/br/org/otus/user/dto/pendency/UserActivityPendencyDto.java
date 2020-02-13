package br.org.otus.user.dto.pendency;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.ccem.otus.exceptions.Dto;

public class UserActivityPendencyDto implements Dto { //TODO pq implementar Dto?

  @SerializedName(value = "nPerPage", alternate = "numPerPage")
  private int numberPerPage;

  @SerializedName(value = "skipQuantity", alternate = "skip")
  private int skipQuantity;

  @SerializedName(value = "order")
  private UserActivityPendencyRequestOrderDto orderDto;

  @SerializedName(value = "filter")
  private UserActivityPendencyRequestFilterDto filterDto;


  public int getNumberPerPage() {
    return numberPerPage;
  }

  public int getSkipQuantity() {
    return skipQuantity;
  }

  public UserActivityPendencyRequestOrderDto getOrderDto() {
    return orderDto;
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
