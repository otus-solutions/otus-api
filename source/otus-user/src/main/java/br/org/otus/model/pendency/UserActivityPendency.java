package br.org.otus.model.pendency;

import br.org.otus.utils.ObjectIdAdapter;
import br.org.otus.utils.ObjectIdToStringAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

import java.util.Objects;

public class UserActivityPendency {

  private ObjectId _id;
  private String objectType;
  private String creationDate;
  private String dueDate;
  private String requester;
  private String receiver;
  private ActivityInfo activityInfo;

  public ObjectId getId() {
    return _id;
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

  public void setRequester(String requester) {
    this.requester = requester;
  }

  public void setReceiver(String receiver) { this.receiver = receiver; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return Objects.equals(_id, ((UserActivityPendency) o).getId());
  }

  public static String serialize(UserActivityPendency userActivityPendency) {
    return getGsonBuilder().create().toJson(userActivityPendency);
  }

  public static UserActivityPendency deserialize(String userActivityPendencyJson) {
    return UserActivityPendency.getGsonBuilder().create().fromJson(userActivityPendencyJson, UserActivityPendency.class);
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
