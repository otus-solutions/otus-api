package br.org.otus.outcomes;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.ArrayList;

public class ParticipantEventDTO implements Dto {

  public String _id;
  public String objectType;
  public String name;
  public String acronym;
  public String activityId;
  public String description;
  public EmailNotification emailNotification;

  public ParticipantEventDTO(String acronym, String activityId) {
    this.objectType = "ParticipantActivityAutoFillEvent"; //todo check object types
    this.name = acronym;
    this.acronym = acronym;
    this.description = acronym; //todo: check what fix description to use
    this.activityId = activityId;
  }

  public String getObjectType() {
    this.updateObjectType();
    return this.objectType;
  }

  public void updateObjectType() {
    this.objectType = "Participant".concat(this.objectType);
  }

  public String getActivityId() {
    return this.activityId;
  }


  public static String serialize (ParticipantEventDTO participantEventDTO) {
    return getGsonBuilder().create().toJson(participantEventDTO);
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
