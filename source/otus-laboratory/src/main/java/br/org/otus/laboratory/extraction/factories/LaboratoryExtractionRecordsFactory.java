package br.org.otus.laboratory.extraction.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;

public class LaboratoryExtractionRecordsFactory {

  private LinkedList<LaboratoryRecordExtraction> inputRecords;
  private List<List<Object>> outputRecords;

  public LaboratoryExtractionRecordsFactory(LinkedList<LaboratoryRecordExtraction> records) {
    this.inputRecords = records;
    this.outputRecords = new LinkedList<>();
  }

  public List<List<Object>> getRecords() {
    return this.outputRecords;
  }

  public void buildResultInformation() {
    Collections.sort(inputRecords);

    inputRecords.forEach(record -> {
      record.getResults().forEach(result -> {
        List<String> answers = new LinkedList<String>();
        answers.add(result.getRecruitmentNumber().toString());
        if (result.getUnattachedLaboratoryId() != null) {
          answers.add(result.getUnattachedLaboratoryId().toString());
        } else {
          answers.add("");
        }
        /* information of tube */
        answers.add(result.getTubeCode());
        answers.add(result.getTubeQualityControl().toString());
        answers.add(result.getTubeType().toString());
        answers.add(result.getTubeMoment().toString());
        if (result.getTubeCollectionDate() != null) {
          answers.add(result.getTubeCollectionDate());
        } else {
          answers.add("");
        }
        if (result.getTubeResponsible() != null) {
          answers.add(result.getTubeResponsible());
        } else {
          answers.add("");
        }
        /* information of aliquot */
        answers.add(result.getAliquotCode());
        answers.add(result.getAliquotName());
        answers.add(result.getAliquotContainer());
        answers.add(result.getAliquotProcessingDate());
        answers.add(result.getAliquotRegisterDate());
        answers.add(result.getAliquotResponsible());
        answers.add(result.getAliquotRole());
        answers.add(result.getHasTransportationLotId().toString());
        answers.add(result.getHasExamLotId().toString());

        this.outputRecords.add(new ArrayList<>(answers));
      });
    });
  }

}
