package br.org.otus.laboratory.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.laboratory.extraction.model.ParticipantLaboratoryResultExtraction;

public class LaboratoryExtractionRecordsFactory {

  private List<String> headers;
  private LinkedList<ParticipantLaboratoryResultExtraction> inputRecords;
  private List<List<Object>> outputRecords;

  public LaboratoryExtractionRecordsFactory(List<String> headers, LinkedList<ParticipantLaboratoryResultExtraction> records) {
    this.headers = headers;
    this.inputRecords = records;
    this.outputRecords = new LinkedList<>();
  }

  public List<List<Object>> getRecords() {
    return this.outputRecords;
  }

  public void buildResultInformation() {
    inputRecords.forEach(record -> {
      List<String> answers = new LinkedList<String>();
      answers.add(record.getRecruitmentNumber().toString());
      /* information of tube */
      answers.add(record.getTubeCode());
      answers.add(record.getTubeQualityControl().toString());
      answers.add(record.getTubeType().toString());
      answers.add(record.getTubeMoment().toString());
      answers.add(record.getTubeCollectionDate());
      answers.add(record.getTubeResponsible());
      answers.add(record.getRecruitmentNumber().toString());
      /* information of aliquot */
      answers.add(record.getAliquotCode());
      answers.add(record.getAliquotName());
      answers.add(record.getAliquotProcessingDate());
      answers.add(record.getAliquotRegisterDate());
      answers.add(record.getAliquotResponsible());

      this.outputRecords.add(new ArrayList<>(answers));
    });
  }

}
