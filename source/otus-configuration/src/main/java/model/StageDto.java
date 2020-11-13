package model;

import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

import java.util.List;

public class StageDto extends SerializableModelWithID {

  private String acronym;
  private List<ObjectId> stageIds;

  public String getAcronym() {
    return acronym;
  }

  public List<ObjectId> getStageIds() {
    return stageIds;
  }

  public static StageDto deserialize(String json){
    return (StageDto)deserialize(json, StageDto.class);
  }
}
