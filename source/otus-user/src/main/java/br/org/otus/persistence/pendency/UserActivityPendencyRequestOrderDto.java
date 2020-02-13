package br.org.otus.persistence.pendency;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class UserActivityPendencyRequestOrderDto {

  @SerializedName(value = "rn")
  private Integer rnSortMode;

  @SerializedName(value = "externalID")
  private Integer externalIDSortMode;

  @SerializedName(value = "requester")
  private Integer requesterSortMode;

  @SerializedName(value = "receiver")
  private Integer receiverSortMode;

  public Integer getRnSortMode() {
    return rnSortMode;
  }

  public Integer getExternalIDSortMode() {
    return externalIDSortMode;
  }

  public Integer getRequesterSortMode() {
    return requesterSortMode;
  }

  public Integer getReceiverSortMode() {
    return receiverSortMode;
  }

  public static String serialize(UserActivityPendencyRequestOrderDto userActivityPendencyRequestOrderDto) {
    return getGsonBuilder().create().toJson(userActivityPendencyRequestOrderDto);
  }

  public static UserActivityPendencyRequestOrderDto deserialize(String orderRequestJson) {
    return UserActivityPendencyRequestOrderDto.getGsonBuilder().create().fromJson(orderRequestJson, UserActivityPendencyRequestOrderDto.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

  @Override
  public String toString(){ //.
    String str = "order dto: \n";
    str += "rn " + this.getRnSortMode() + "\n";
    str += "externalId " + this.getExternalIDSortMode() + "\n";
    str += "requester " + this.getRequesterSortMode() + "\n";
    str += "receiver " + this.getReceiverSortMode() + "\n";
    return str;
  }
}
