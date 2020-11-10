package model;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

import java.util.ArrayList;
import java.util.List;

public class Stage extends SerializableModelWithID {

  @SerializedName("_id")
  private ObjectId id;
  private String objectType;
  private String name;
  private List<String> availableSurveys;

  public Stage(){
    this.objectType = "Stage";
    this.availableSurveys = new ArrayList<>();
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

  public List<String> getAvailableSurveys() {
    return availableSurveys;
  }

  public void setAvailableSurveys(List<String> availableSurveys) {
    this.availableSurveys = availableSurveys;
  }

  public void addAvailableSurveyInStage(String acronym){
    this.availableSurveys.add(acronym);
  }

  public void removeAvailableSurveyInStage(String acronym){
    this.availableSurveys.remove(acronym);
  }

  public static Stage deserialize(String stageJson) {
    return (Stage)deserialize(stageJson, Stage.class);
  }

}
