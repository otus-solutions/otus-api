package model;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class Stage {

  @SerializedName("_id")
  private ObjectId id;
  private String objectType;
  private String name;

  public Stage(){
    this.objectType = "Stage";
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static Stage deserialize(String stageJson) {
    return getGsonBuilder().create().fromJson(stageJson, Stage.class);
  }

  public static String serialize(Stage stage) {
    return getGsonBuilder().create().toJson(stage);
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
