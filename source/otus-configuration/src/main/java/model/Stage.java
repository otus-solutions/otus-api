package model;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

public class Stage extends SerializableModelWithID {

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
    return (Stage)deserialize(stageJson, Stage.class);
  }

}
