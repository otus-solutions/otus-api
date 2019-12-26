package br.org.otus.model.pendency;

import br.org.otus.utils.ObjectIdAdapter;
import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

public class UserActivityPendency {

  @Equalization(name = "_id")
  private ObjectId id;

  @Equalization(name = "objectType")
  private String objectType;

  @Equalization(name = "creationDate")
  private String creationDate;

  @Equalization(name = "dueDate")
  private String dueDate;

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

  public String getCreationDate() {
    return creationDate;
  }

  public String getDueDate() {
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
    GsonBuilder builder = UserActivityPendency.getGsonBuilder();
    return builder.create().fromJson(userActivityPendencyJson, UserActivityPendency.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    return builder;
  }
}
