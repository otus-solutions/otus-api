package br.org.otus.laboratory.participant;

import br.org.otus.laboratory.participant.tube.Tube;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;

public class TubeParticipantLaboratory {

  private Long recruitmentNumber;
  private Tube tube;

  public TubeParticipantLaboratory(Long recruitmentNumber, Tube tube) {
    this.recruitmentNumber = recruitmentNumber;
    this.tube = tube;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public Tube getTubes() {
    return tube;
  }


  public static String serialize(TubeParticipantLaboratory laboratory) {
    GsonBuilder builder = TubeParticipantLaboratory.getGsonBuilder();
    return builder.create().toJson(laboratory);
  }

  public static TubeParticipantLaboratory deserialize(String laboratoryJson) {
    GsonBuilder builder = TubeParticipantLaboratory.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(laboratoryJson, TubeParticipantLaboratory.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.serializeNulls();

    return builder;
  }
}
