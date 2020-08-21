package org.ccem.otus.model.survey.activity.sharing;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.DateAdapter;

public class ActivitySharing {

  private String objectType;
  @SerializedName("_id")
  private ObjectId id;
  private ObjectId activityID;
  private String token;
  private String email;
  private String creationDate;
  private String expirationDate;
  private double expirationTime;

  public ActivitySharing() { }

  public ActivitySharing(ObjectId activityID, String token, String email) {
    this.activityID = activityID;
    this.token = token;
    this.email = email;
    this.creationDate = DateAdapter.getNowAsISODate();
  }

  public String getObjectType() {
    return objectType;
  }

  public ObjectId getId() {
    return id;
  }

  public ObjectId getActivityID() {
    return activityID;
  }

  public String getToken() {
    return token;
  }

  public String getEmail() {
    return email;
  }

  public String getCreationDate() {
    return creationDate;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public double getExpirationTime() {
    return expirationTime;
  }
}
