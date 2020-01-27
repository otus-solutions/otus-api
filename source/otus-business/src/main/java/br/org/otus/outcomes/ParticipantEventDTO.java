package br.org.otus.outcomes;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class ParticipantEventDTO implements Dto {

  public String _id;
  public String objectType;
  public String acronym;
  public String description;
  public String activityId;

  public String getObjectType(){
    this.updateObjectType();
    return this.objectType;
  }

  public void updateObjectType() {
    this.objectType = "Participant".concat(this.objectType);
  }

  public String getActivityId(){
    return this.activityId;
  }

  public static ParticipantEventDTO deserialize(String participantEventJson) {
    ParticipantEventDTO participantEventDTO = ParticipantEventDTO.getGsonBuilder().create().fromJson(participantEventJson, ParticipantEventDTO.class);
    return participantEventDTO;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }

  @Override
  public Boolean isValid() {
    return !objectType.isEmpty();
  }
}
