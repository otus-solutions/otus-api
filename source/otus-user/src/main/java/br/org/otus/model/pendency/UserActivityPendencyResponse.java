package br.org.otus.model.pendency;

import br.org.otus.utils.ObjectIdAdapter;
import br.org.otus.utils.ObjectIdToStringAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

import java.util.Objects;

public class UserActivityPendencyResponse extends UserActivityPendency {

  private ActivityInfo activityInfo;

  public ActivityInfo getActivityInfo() {
    return activityInfo;
  }

  public static String serialize(UserActivityPendencyResponse userActivityPendencyResponse) {
    return getGsonBuilder().create().toJson(userActivityPendencyResponse);
  }

  public static UserActivityPendencyResponse deserialize(String userActivityPendencyResponseJson) {
    return UserActivityPendencyResponse.getGsonBuilder().create().fromJson(userActivityPendencyResponseJson, UserActivityPendencyResponse.class);
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
