package br.org.otus.examUploader.business.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    answers.add(record.getResultName());
    answers.add(record.getValue());
    answers.add(record.getReleaseDate());
    String observations = "";
    for (Observation observation : record.getObservations()) {
      if (observations.isEmpty()) {
        observations += observation.getValue();
      } else {
        observations += ", " + observation.getValue();
      }
    }
    answers.add(observations);

    return answers;
  }

}
