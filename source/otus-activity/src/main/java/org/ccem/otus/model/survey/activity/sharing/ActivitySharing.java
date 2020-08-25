package org.ccem.otus.model.survey.activity.sharing;

import br.org.otus.utils.ObjectIdAdapter;
import br.org.otus.utils.ObjectIdToStringAdapter;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.DateAdapter;

import java.text.ParseException;

public class ActivitySharing {

  public static final int EXPIRATION_TIME = 7;

  private String objectType;
  @SerializedName("_id")
  private ObjectId id;
  private ObjectId activityID;
  private ObjectId userID;
  private String participantToken;
  private String creationDate;
  private String expirationDate;

  public ActivitySharing() { }

  public ActivitySharing(ObjectId activityID, ObjectId userID, String participantToken) {
    this.activityID = activityID;
    this.userID = userID;
    this.participantToken = participantToken;
    this.creationDate = DateAdapter.nowToISODate();
    try {
      this.expirationDate = DateAdapter.getDatePlusDays(this.creationDate, EXPIRATION_TIME);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public String getObjectType() {
    return objectType;
  }

  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }

  public ObjectId getId() {
    return id;
  }

  public ObjectId getActivityID() {
    return activityID;
  }

  public String getParticipantToken() {
    return participantToken;
  }

  public ObjectId getUserID() {
    return userID;
  }

  public String getCreationDate() {
    return creationDate;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public static String serialize(ActivitySharing activitySharing) {
    return getGsonBuilder().create().toJson(activitySharing);
  }

  public static ActivitySharing deserialize(String activitySharingJson) {
    return getGsonBuilder().create().fromJson(activitySharingJson, ActivitySharing.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    return builder;
  }

  public static GsonBuilder getFrontGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }
}
