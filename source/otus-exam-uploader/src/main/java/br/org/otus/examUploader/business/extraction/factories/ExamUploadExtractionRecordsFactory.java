package br.org.otus.examUploader.business.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.examUploader.ExtraVariable;
import br.org.otus.examUploader.Observation;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;

public class ExamUploadExtractionRecordsFactory {

  private LinkedList<ParticipantExamUploadResultExtraction> inputRecords;
  private List<List<Object>> outputRecords;

  public ExamUploadExtractionRecordsFactory(LinkedList<ParticipantExamUploadResultExtraction> records) {
    this.inputRecords = records;
    this.outputRecords = new LinkedList<>();
  }

  public List<List<Object>> getRecords() {
    return this.outputRecords;
  }

  public void buildResultInformation() {
    inputRecords.forEach(record -> {
      this.outputRecords.add(new ArrayList<>(this.createRecordsAnswers(record)));
    });
  }

  private List<String> createRecordsAnswers(ParticipantExamUploadResultExtraction record) {
    List<String> answers = new LinkedList<String>();
    answers.add(record.getRecruitmentNumber().toString());
    answers.add(record.getCode());
    answers.add(record.getExamName());
    answers.add(record.getResultName());
    answers.add(record.getValue());
    answers.add(record.getReleaseDate());
    answers.add(record.getRealizationDate());

    String observations = "";
    for (Observation observation : record.getObservations()) {
      if (observations.isEmpty()) {
        observations += observation.getValue();
      } else {
        observations += ", " + observation.getValue();
      }
    }
    answers.add(observations);

    answers.add(record.getCutOffValue());

    StringBuilder extraVariables = new StringBuilder();
    List<ExtraVariable> recordExtraVariables = record.getExtraVariables();
    if (recordExtraVariables != null){
      for (ExtraVariable extraVariable : recordExtraVariables) {
        if (extraVariables.length() == 0) {
          extraVariables.append(extraVariable.getName()).append(": '").append(extraVariable.getValue()).append("'");
        } else {
          extraVariables.append("; ").append(extraVariable.getName()).append(": '").append(extraVariable.getValue()).append("'");
        }
      }
    }
    answers.add(extraVariables.toString());

    return answers;
  }

}
