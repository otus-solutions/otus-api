package br.org.otus.model.pendency;

import br.org.otus.utils.ObjectIdAdapter;
import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

import java.util.Date;

public class UserActivityPendency {

  @Equalization(name = "_id")
  private ObjectId id;

  @Equalization(name = "objectType")
  private String objectType;

  @Equalization(name = "creationDate")
  private Date creationDate;

  @Equalization(name = "dueDate")
  private Date dueDate;

  @Equalization(name = "requester")
  private String requester;

  @Equalization(name = "receiver")
  private String receiver;

  @Equalization(name = "activityInfo")
  private ActivityInfo activityInfo;

  public UserActivityPendency() {
    this.id = null;
    this.objectType = null;
    this.creationDate = null;
    this.dueDate = null;
    this.requester = null;
    this.receiver = null;
    this.activityInfo = null;
  }

  public ObjectId getId() {
    return id;
  }

  public String getObjectType() {
    return objectType;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public String getRequester() {
    return requester;
  }

  public String getReceiver() {
    return receiver;
  }

  public ActivityInfo getActivityInfo() {
    return activityInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserActivityPendency that = (UserActivityPendency) o;

    return id != null ? id.equals(that.getId()) : that.getId() == null;
  }

  public static String serialize(UserActivityPendency userActivityPendencyJson) {
    return getGsonBuilder().create().toJson(userActivityPendencyJson);
  }

  public static UserActivityPendency deserialize(String userActivityPendencyJson) {
    return UserActivityPendency.getGsonBuilder().create().fromJson(userActivityPendencyJson, UserActivityPendency.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter()); //TODO
    builder.serializeNulls();
    return builder;
  }
}
