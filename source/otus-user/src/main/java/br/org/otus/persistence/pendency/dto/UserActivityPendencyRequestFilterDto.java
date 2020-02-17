package br.org.otus.persistence.pendency.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class UserActivityPendencyRequestFilterDto {

  public static final String FINALIZED_STATUS = "FINALIZED";
  public static final String NOT_FINALIZED_STATUS = "NOT_FINALIZED";

  @SerializedName(value = "status")
  private String status;

  @SerializedName(value = "acronym")
  private String acronym;

  @SerializedName(value = "rn")
  private Long rn;

  @SerializedName(value = "requester")
  private String[] requester;

  @SerializedName(value = "receiver")
  private String[] receiver;


  public String getAcronym() { return acronym; }

  public Long getRn() { return rn; }

  public String getStatus() { return status; }

  public String[] getRequesters() {
    return requester;
  }

  public String[] getReceivers() {
    return receiver;
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