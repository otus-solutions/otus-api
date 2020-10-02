package model;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class Stage {

  @SerializedName("_id")
  private ObjectId id;

  private String name;

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
    return new GsonBuilder().create().fromJson(stageJson, Stage.class);
  }

  public static String serialize(Stage stage) {
    return new GsonBuilder().create().toJson(stage);
  }

  public static GsonBuilder getFrontGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }

}
