package br.org.otus.laboratory.project.exam.utils;

import br.org.otus.laboratory.project.utils.LabObjectIdAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.utils.LongAdapter;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.time.LocalDateTime;

public class ExamResultTube {
  private String tubeCode;
  private Long recruitmentNumber;
  private Boolean isCollected;
  private String name;
  private Participant participantData;

  public static ExamResultTube deserialize(String examResultTubeJson) {
    return getGsonBuilder().create().fromJson(examResultTubeJson, ExamResultTube.class);
  }

  private static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new LabObjectIdAdapter());
    builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder;
  }

  public String getTubeCode() {
    return tubeCode;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getName() {
    return name;
  }

  public Participant getParticipantData() {
    return participantData;
  }

  public Boolean getCollected() {
    return isCollected;
  }
}
