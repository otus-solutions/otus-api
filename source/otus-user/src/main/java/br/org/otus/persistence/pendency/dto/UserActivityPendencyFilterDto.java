package br.org.otus.persistence.pendency.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.ccem.otus.exceptions.Dto;

public class UserActivityPendencyFilterDto implements Dto {

  @SerializedName(value = "status")
  private String status;

  @SerializedName(value = "acronym")
  private String acronym;

  @SerializedName(value = "rn")
  private Long rn;

  @SerializedName(value = "externalID")
  private String externalID;

  @SerializedName(value = "dueDate")
  private String dueDate;

  @SerializedName(value = "requester")
  private String[] requester;

  @SerializedName(value = "receiver")
  private String[] receiver;


  public String getAcronym() {
    return acronym;
  }

  public Long getRn() {
    return rn;
  }

  public String getExternalID() {
    return externalID;
  }

  public String getDueDate() {
    return dueDate;
  }

  public String getStatus() {
    return status;
  }

  public String[] getRequesters() {
    return requester;
  }

  public String[] getReceivers() {
    return receiver;
  }


  public static String serialize(UserActivityPendencyFilterDto userActivityPendencyFilterDto) {
    return getGsonBuilder().create().toJson(userActivityPendencyFilterDto);
  }

  public static UserActivityPendencyFilterDto deserialize(String filterRequestJson) {
    return UserActivityPendencyFilterDto.getGsonBuilder().create().fromJson(filterRequestJson, UserActivityPendencyFilterDto.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }


  @Override
  public Boolean isValid() {
    return (getStatus() == null || UserActivityPendencyStatusFilterOptions.contains(getStatus())) &&
      arrayIsValid(requester) &&
      arrayIsValid(receiver);
  }
  private boolean arrayIsValid(String[] array){
    if(array == null){
      return true;
    }
    return (array.length > 0);
  }

}