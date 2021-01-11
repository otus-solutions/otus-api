package org.ccem.otus.participant.model.participantContactAttempt;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MetadataAttemptStatus {

  private ObjectId _id;
  private String objectType;
  private ArrayList metadataOptions;

  public MetadataAttemptStatus(String objectType, ArrayList metadataOptions) {
    this.objectType = objectType;
    this.metadataOptions = metadataOptions;
  }

  public static String serialize(MetadataAttemptStatus participantContactJson) {
    GsonBuilder builder = MetadataAttemptStatus.getGsonBuilder();
    return builder.create().toJson(participantContactJson);
  }

  public static MetadataAttemptStatus deserialize(String participantJson) {
    GsonBuilder builder = MetadataAttemptStatus.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(participantJson, MetadataAttemptStatus.class);
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
