package org.ccem.otus.service.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;

public class ActivityProgressRecordsFactory {

  private LinkedList<ActivityProgressResultExtraction> inputRecords;
  private List<List<Object>> outputRecords;

  public ActivityProgressRecordsFactory(LinkedList<ActivityProgressResultExtraction> records) {
    this.inputRecords = records;
    this.outputRecords = new LinkedList<>();

    this.buildResultInformation();
  }

  public List<List<Object>> getRecords() {
    return this.outputRecords;
  }

  private void buildResultInformation() {
    inputRecords.forEach(record -> {
      this.outputRecords.add(new ArrayList<>(this.createRecordsAnswers(record)));
    });
  }

  private List<String> createRecordsAnswers(ActivityProgressResultExtraction record) {
    List<String> answers = new LinkedList<String>();
    answers.add(record.getRecruitmentNumber().toString());
    answers.add(record.getAcronym());
    answers.add(record.getStatus());
    answers.add(record.getStatusDate());
    answers.add(record.getInapplicabilityObservation());

    return answers;
  }

}
