package org.ccem.otus.model.monitoring;

import br.org.otus.laboratory.project.exam.examInapplicability.ExamInapplicability;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class ParticipantExamReportDto {

  private ArrayList<ParticipantExam> participantExams;

  public ParticipantExamReportDto(List<String> examNames) {
    participantExams = new ArrayList<>();
    for (String exam : examNames) {
      participantExams.add(new ParticipantExam(exam, 0));
    }
  }

  private class ParticipantExam {
    private String name;
    private Integer quantity;
    private ExamInapplicability doesNotApply;

    public ParticipantExam(String exam, int quantity) {
      this.name = exam;
      this.quantity = quantity;
    }
  }

  public static String serialize(ParticipantExamReportDto reportTemplate) {
    return ParticipantExamReportDto.getGsonBuilder().create().toJson(reportTemplate);
  }

  public static ParticipantExamReportDto deserialize(String reportTemplateJson) {
    ParticipantExamReportDto reportTemplate = ParticipantExamReportDto.getGsonBuilder().create().fromJson(reportTemplateJson, ParticipantExamReportDto.class);
    return reportTemplate;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }


}
