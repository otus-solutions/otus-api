package org.ccem.otus.participant.model.participantContactAttempt;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.time.LocalDateTime;

public class ParticipantContactAttempt {

  private ObjectId _id;
  private String objectType;
  private Long recruitmentNumber;
  private Object address;
  private LocalDateTime attemptDateTime;
  private Object attemptStatus;
  private String registeredBy;

  public ParticipantContactAttempt(String objectType, Long recruitmentNumber, Object address, LocalDateTime attemptDateTime, Object attemptStatus, String registeredBy) {
    this.objectType = objectType;
    this.recruitmentNumber = recruitmentNumber;
    this.address = address;
    this.attemptDateTime = attemptDateTime;
    this.attemptStatus = attemptStatus;
    this.registeredBy = registeredBy;
  }

  public Object getAddress() {
    return address;
  }

  public static String serialize(ParticipantContactAttempt participantContactJson) {
    GsonBuilder builder = ParticipantContactAttempt.getGsonBuilder();
    return builder.create().toJson(participantContactJson);
  }

  public static ParticipantContactAttempt deserialize(String participantJson) {
    GsonBuilder builder = ParticipantContactAttempt.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(participantJson, ParticipantContactAttempt.class);
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
