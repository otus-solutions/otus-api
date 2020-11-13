package model;

import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

import java.util.List;

public class StageDto extends SerializableModelWithID {

  private String acronym;
  private List<ObjectId> stageIdsToAdd;
  private List<ObjectId> stageIdsToRemove;

  public String getAcronym() {
    return acronym;
  }

  public List<ObjectId> getStageIdsToAdd() {
    return stageIdsToAdd;
  }

  public List<ObjectId> getStageIdsToRemove() {
    return stageIdsToRemove;
  }

  public static StageDto deserialize(String json){
    return (StageDto)deserialize(json, StageDto.class);
  }
}
