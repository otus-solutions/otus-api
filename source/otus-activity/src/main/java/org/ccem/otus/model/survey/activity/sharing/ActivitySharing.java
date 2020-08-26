package org.ccem.otus.model.survey.activity.sharing;

import br.org.otus.utils.ObjectIdAdapter;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.DateAdapter;

import java.text.ParseException;

public class ActivitySharing {

  public static final int EXPIRATION_TIME = 7;
  private static final String OBJECT_TYPE = "ActivitySharing";

  private String objectType;
  @SerializedName("_id")
  private ObjectId id;
  private ObjectId activityId;
  private ObjectId userId;
  private String participantToken;
  private String creationDate;
  private String expirationDate;

  public ActivitySharing() { }

  public ActivitySharing(ObjectId activityId, ObjectId userId, String participantToken) {
    this.objectType = OBJECT_TYPE;
    this.activityId = activityId;
    this.userId = userId;
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

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public ObjectId getActivityId() {
    return activityId;
  }

  public String getParticipantToken() {
    return participantToken;
  }

  public ObjectId getUserId() {
    return userId;
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

  public void renovate(){
    try {
      this.expirationDate = DateAdapter.getDatePlusDays(DateAdapter.nowToISODate(), EXPIRATION_TIME);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
