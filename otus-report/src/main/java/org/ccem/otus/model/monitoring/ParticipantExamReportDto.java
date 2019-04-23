package org.ccem.otus.model.monitoring;

import br.org.otus.laboratory.project.exam.examInapplicability.ExamInapplicability;
import com.google.gson.GsonBuilder;

public class ParticipantExamReportDto {

  private String name;
  private Integer quantity;
  private ExamInapplicability doesNotApply;

  public static String serialize(ParticipantExamReportDto reportTemplate) {
    return ParticipantExamReportDto.getGsonBuilder().create().toJson(reportTemplate);
  }

  public static ParticipantExamReportDto deserialize(String reportTemplateJson) {
    ParticipantExamReportDto reportTemplate = ParticipantExamReportDto.getGsonBuilder().create().fromJson(reportTemplateJson, ParticipantExamReportDto.class);
    return reportTemplate;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();

    builder.serializeNulls();
    return builder;
  }
}
