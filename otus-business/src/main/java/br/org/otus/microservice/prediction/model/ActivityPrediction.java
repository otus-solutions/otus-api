package br.org.otus.microservice.prediction.model;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.auditor.adapter.LocalDateTimeAdapter;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityPrediction {
  private ObjectId activityId;
  private LocalDateTime predictionDate;
  private Set<QuestionForPrediction> questions;

  public ActivityPrediction() {
    this.predictionDate = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
    questions = new HashSet<>();
  }

  public void addQuestionForPrediction(QuestionForPrediction question) {
    if (validateQuestionType(question)) {
      questions.add(question);
    }
  }

  public void setActivityId(ObjectId activityId) {
    this.activityId = activityId;
  }

  private Boolean validateQuestionType(QuestionForPrediction question) {
    return Arrays.asList("SingleSelectionQuestion", "CheckboxQuestion").contains(question.getType());
  }

  public String serialize() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.serializeNulls();
    return builder.create().toJson(this);
  }
}
