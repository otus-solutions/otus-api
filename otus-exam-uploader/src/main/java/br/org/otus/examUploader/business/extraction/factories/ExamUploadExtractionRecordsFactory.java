package br.org.otus.examUploader.business.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;

public class ExamUploadExtractionRecordsFactory {

  private LinkedList<ParticipantExamUploadRecordExtraction> inputRecords;
  private List<List<Object>> outputRecords;

  public ExamUploadExtractionRecordsFactory(LinkedList<ParticipantExamUploadRecordExtraction> records) {
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

  private List<String> createRecordsAnswers(ParticipantExamUploadRecordExtraction record) {
    List<String> answers = new LinkedList<String>();

    answers.add(record.getRecruitmentNumber().toString());
    answers.add(record.getAliquotCode());
    answers.add(record.getResultName());
    answers.add(record.getValue());
    answers.add(record.getReleaseDate());
    String observations = "";
    record.getObservations().forEach(observation -> {
      if (observations.isEmpty()) {
        observations.concat(observation.getValue());
      } else {
        observations.concat(", " + observation.getValue());
      }
    });
    answers.add(observations);

    return answers;
  }

}
