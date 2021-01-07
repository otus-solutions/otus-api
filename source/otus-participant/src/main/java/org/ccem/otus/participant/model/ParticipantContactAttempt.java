package org.ccem.otus.participant.model;

import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.bcel.internal.generic.ObjectType;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.participant_contact.Address;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParticipantContactAttempt {

  private ObjectId _id;
  private String objectType;
  private Long recruitmentNumber;
  private Object address;
  private LocalDateTime attemptDateTime;
  private String attemptStatus;

  public ParticipantContactAttempt(String objectType, Long recruitmentNumber, Object address, LocalDateTime attemptDateTime, String attemptStatus) {
    this.objectType = objectType;
    this.recruitmentNumber = recruitmentNumber;
    this.address = address;
    this.attemptDateTime = attemptDateTime;
    this.attemptStatus = attemptStatus;
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
