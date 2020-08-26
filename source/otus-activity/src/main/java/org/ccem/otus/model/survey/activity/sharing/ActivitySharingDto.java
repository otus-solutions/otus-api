package org.ccem.otus.model.survey.activity.sharing;

import br.org.otus.utils.ObjectIdAdapter;
import br.org.otus.utils.ObjectIdToStringAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.Dto;

public class ActivitySharingDto implements Dto {

  private ActivitySharing activitySharing;
  private String url;

  public ActivitySharingDto(ActivitySharing activitySharing, String url) {
    this.activitySharing = activitySharing;
    this.url = url;
  }

  public ActivitySharing getActivitySharing() {
    return activitySharing;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public Boolean isValid() {
    return activitySharing != null && url != null;
  }

  public static String serialize(ActivitySharingDto activitySharing) {
    return getGsonBuilder().create().toJson(activitySharing);
  }

  public static ActivitySharingDto deserialize(String activitySharingDtoJson) {
    return getGsonBuilder().create().fromJson(activitySharingDtoJson, ActivitySharingDto.class);
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
