package br.org.otus.model.userActivityPendency;

import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
//import org.ccem.otus.utils.ObjectIdAdapter;
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

//  public static String serialize(ActivityInfo surveyGroup) {
//    return getGsonBuilder().create().toJson(surveyGroup);
//  }
//
//  public static ActivityInfo deserialize(String surveyGroupJson) {
//    return ActivityInfo.getGsonBuilder().create().fromJson(surveyGroupJson, ActivityInfo.class);
//  }
//
//  public static GsonBuilder getGsonBuilder() {
//    GsonBuilder builder = new GsonBuilder();
//    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
//    builder.serializeNulls();
//    return builder;
//  }
}
