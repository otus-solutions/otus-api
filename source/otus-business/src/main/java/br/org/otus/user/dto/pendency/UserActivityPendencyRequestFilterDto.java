package br.org.otus.user.dto.pendency;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserActivityPendencyRequestFilterDto {

  @SerializedName(value = "finalized")
  private Boolean finalized;

  @SerializedName(value = "requester")
  private String requester;
  //  private List<String> requester;

  @SerializedName(value = "receiver")
  private String receiver;
  //  private List<String> receiver;

  public Boolean getFinalized() {
    return finalized;
  }

  public void setFinalized(Boolean finalized) {
    this.finalized = finalized;
  }

//  public List<String> getRequester() {
//    return requester;
//  }
//
//  public void setRequester(List<String> requester) {
//    this.requester = requester;
//  }
//
//  public List<String> getReceiver() {
//    return receiver;
//  }
//
//  public void setReceiver(List<String> receiver) {
//    this.receiver = receiver;
//  }


  public String getRequester() {
    return requester;
  }

  public void setRequester(String requester) {
    this.requester = requester;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public static String serialize(UserActivityPendencyRequestFilterDto userActivityPendencyRequestFilterDto) {
    return getGsonBuilder().create().toJson(userActivityPendencyRequestFilterDto);
  }

  public static UserActivityPendencyRequestFilterDto deserialize(String orderRequestJson) {
    return UserActivityPendencyRequestFilterDto.getGsonBuilder().create().fromJson(orderRequestJson, UserActivityPendencyRequestFilterDto.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }
}
