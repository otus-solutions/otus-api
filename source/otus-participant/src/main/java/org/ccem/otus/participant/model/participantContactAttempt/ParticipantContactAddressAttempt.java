package org.ccem.otus.participant.model.participantContactAttempt;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParticipantContactAddressAttempt {

  private String _id;
  private ArrayList<ParticipantContactAttempt> attemptList;

  public ParticipantContactAddressAttempt(String _id, ArrayList<ParticipantContactAttempt> attemptList) {
    this._id = _id;
    this.attemptList = attemptList;
  }

  public static String serialize(ParticipantContactAddressAttempt participantContactJson) {
    GsonBuilder builder = ParticipantContactAddressAttempt.getGsonBuilder();
    return builder.create().toJson(participantContactJson);
  }

  public static ParticipantContactAddressAttempt deserialize(String participantJson) {
    GsonBuilder builder = ParticipantContactAddressAttempt.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(participantJson, ParticipantContactAddressAttempt.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder;
  }
}
