package org.ccem.otus.participant.service.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;

public class ParticipantContactAttemptsRecordsFactory {

  private ArrayList<ParticipantContactAttempt>  inputRecords;
  private List<List<Object>> outputRecords;

  public ParticipantContactAttemptsRecordsFactory(ArrayList<ParticipantContactAttempt> records) {
    this.inputRecords = records;
    this.outputRecords = new ArrayList<>();
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

  private List<String> createRecordsAnswers(ParticipantContactAttempt record) {
    List<String> answers = new LinkedList<String>();

    answers.add(record.getRecruitmentNumber().toString());
    answers.add(record.getUserEmail());
    answers.add(record.getAttemptStatus());
    answers.add(record.getAttemptDateTime().toString());
    answers.add(record.getAttemptAddressStreet());
    answers.add(record.getAttemptAddressNumber().toString());
    answers.add(record.getAttemptAddressZipCode());
    answers.add(record.getAttemptAddressComplement());
    answers.add(record.getAttemptAddressDistrict());
    answers.add(record.getAttemptAddressCity());
    answers.add(record.getAttemptAddressState());
    answers.add(record.getAttemptAddressCountry());
    answers.add(record.getAttemptSectorIBGE());

    return answers;
  }

}
