package org.ccem.otus.participant.model.participantContactAttempt;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParticipantContactAttemptConfiguration {

  private ObjectId _id;
  private String objectType;
  private Integer numberOfAttempts;
  private ArrayList<Object> statusMetadata;

  public ParticipantContactAttemptConfiguration(String objectType, Integer numberOfAttempts, ArrayList<Object> statusMetadata) {
    this.objectType = objectType;
    this.numberOfAttempts = numberOfAttempts;
    this.statusMetadata = statusMetadata;
  }

  public Integer getNumberOfAttempts() {
    return numberOfAttempts;
  }

  public static String serialize(ParticipantContactAttemptConfiguration participantContactJson) {
    GsonBuilder builder = ParticipantContactAttemptConfiguration.getGsonBuilder();
    return builder.create().toJson(participantContactJson);
  }

  public static ParticipantContactAttemptConfiguration deserialize(String participantJson) {
    GsonBuilder builder = ParticipantContactAttemptConfiguration.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(participantJson, ParticipantContactAttemptConfiguration.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder;
  }
}
