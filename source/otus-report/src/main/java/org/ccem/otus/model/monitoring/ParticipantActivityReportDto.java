package org.ccem.otus.model.monitoring;

import com.google.gson.GsonBuilder;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.LongAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParticipantActivityReportDto {

  private String acronym;
  private String name;
  private ArrayList<ParticipantActivityRelationship> activities;
  private ActivityInapplicability doesNotApply;

  public static String serialize(ParticipantActivityReportDto reportTemplate) {
    return ParticipantActivityReportDto.getGsonBuilder().create().toJson(reportTemplate);
  }

  public static ParticipantActivityReportDto deserialize(String reportTemplateJson) {
    ParticipantActivityReportDto reportTemplate = ParticipantActivityReportDto.getGsonBuilder().create().fromJson(reportTemplateJson, ParticipantActivityReportDto.class);
    return reportTemplate;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    builder.serializeNulls();
    return builder;
  }

}

