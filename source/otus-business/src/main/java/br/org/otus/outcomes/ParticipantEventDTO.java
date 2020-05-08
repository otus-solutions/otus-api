package br.org.otus.outcomes;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class ParticipantEventDTO implements Dto {

  public String _id;
  public String objectType;
  public String name;
  public String acronym;
  public String activityId;
  public String description;
  public EmailNotification emailNotification;

  public ParticipantEventDTO(String acronym, String name, String description, String activityId) {
    this.objectType = "ActivityAutoFillEvent";
    this.acronym = acronym;
    this.name = name;
    this.description = description;
    this.activityId = activityId;
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

  public static ParticipantEventDTO createActivityAutoFillEvent(String acronym, String name, String activityId) {
    return new ParticipantEventDTO(acronym, name, "Questionário para preenchimento", activityId);
  }

  @Override
  public Boolean isValid() {
    return !objectType.isEmpty();
  }
}
