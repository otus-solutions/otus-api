package org.ccem.otus.participant.business.extraction.factories;


import org.ccem.otus.participant.business.extraction.model.ParticipantResultExtraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParticipantExtractionRecordsFactory {

  private LinkedList<ParticipantResultExtraction> inputRecords;
  private List<List<Object>> outputRecords;

  public ParticipantExtractionRecordsFactory(LinkedList<ParticipantResultExtraction> records) {
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

  private List<String> createRecordsAnswers(ParticipantResultExtraction record) {
    List<String> answers = new LinkedList<String>();
    answers.add(record.getRecruitmentNumber().toString());
    answers.add(record.getName());
    answers.add(record.getSex());
    answers.add(record.getCenter());
    answers.add(record.getBirthdate());
    answers.add(record.getEmail());
    answers.add(record.getRegisteredBy());
    answers.add(record.getMainPhone());
    answers.add(record.getMainPhoneObservation());
    answers.add(record.getSecondPhone());
    answers.add(record.getSecondPhoneObservation());
    answers.add(record.getThirdPhone());
    answers.add(record.getThirdPhoneObservation());
    answers.add(record.getFourthPhone());
    answers.add(record.getFourthPhoneObservation());
    answers.add(record.getFifthPhone());
    answers.add(record.getFifthPhoneObservation());
    answers.add(record.getMainEmail());
    answers.add(record.getMainEmailObservation());
    answers.add(record.getSecondEmail());
    answers.add(record.getSecondEmailObservation());
    answers.add(record.getThirdEmail());
    answers.add(record.getThirdEmailObservation());
    answers.add(record.getFourthEmail());
    answers.add(record.getFourthEmailObservation());
    answers.add(record.getFifthEmail());
    answers.add(record.getFifthEmailObservation());
    answers.add(record.getMainAddressCep());
    answers.add(record.getMainAddressStreet());
    answers.add(record.getMainAddressNumber().toString());
    answers.add(record.getMainAddressComplements());
    answers.add(record.getMainAddressNeighbourhood());
    answers.add(record.getMainAddressCity());
    answers.add(record.getMainAddressUf());
    answers.add(record.getMainAddressCountry());
    answers.add(record.getMainAddressObservation());
    answers.add(record.getSecondAddressCep());
    answers.add(record.getSecondAddressStreet());
    answers.add(record.getSecondAddressNumber().toString());
    answers.add(record.getSecondAddressComplements());
    answers.add(record.getSecondAddressNeighbourhood());
    answers.add(record.getSecondAddressCity());
    answers.add(record.getSecondAddressUf());
    answers.add(record.getSecondAddressCountry());
    answers.add(record.getSecondAddressObservation());
    answers.add(record.getThirdAddressCep());
    answers.add(record.getThirdAddressStreet());
    answers.add(record.getThirdAddressNumber().toString());
    answers.add(record.getThirdAddressComplements());
    answers.add(record.getThirdAddressNeighbourhood());
    answers.add(record.getThirdAddressCity());
    answers.add(record.getThirdAddressUf());
    answers.add(record.getThirdAddressCountry());
    answers.add(record.getThirdAddressObservation());
    answers.add(record.getFourthAddressCep());
    answers.add(record.getFourthAddressStreet());
    answers.add(record.getFourthAddressNumber().toString());
    answers.add(record.getFourthAddressComplements());
    answers.add(record.getFourthAddressNeighbourhood());
    answers.add(record.getFourthAddressCity());
    answers.add(record.getFourthAddressUf());
    answers.add(record.getFourthAddressCountry());
    answers.add(record.getFourthAddressObservation());
    answers.add(record.getFifthAddressCep());
    answers.add(record.getFifthAddressStreet());
    answers.add(record.getFifthAddressNumber().toString());
    answers.add(record.getFifthAddressComplements());
    answers.add(record.getFifthAddressNeighbourhood());
    answers.add(record.getFifthAddressCity());
    answers.add(record.getFifthAddressUf());
    answers.add(record.getFifthAddressCountry());
    answers.add(record.getFifthAddressObservation());
    return answers;
  }

}
