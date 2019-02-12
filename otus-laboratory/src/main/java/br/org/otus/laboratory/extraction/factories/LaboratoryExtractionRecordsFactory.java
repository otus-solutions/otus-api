package br.org.otus.laboratory.extraction.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.laboratory.extraction.model.ParticipantLaboratoryRecordExtraction;

public class LaboratoryExtractionRecordsFactory {

  private List<String> headers;
  private LinkedList<ParticipantLaboratoryRecordExtraction> inputRecords;
  private List<List<Object>> outputRecords;

  public LaboratoryExtractionRecordsFactory(List<String> headers, LinkedList<ParticipantLaboratoryRecordExtraction> records) {
    this.headers = headers;
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
        /* information of tube */
        if(result.getTubeCode() != null){
          answers.add(result.getTubeCode());
        }else{answers.add("");}
        if(result.getTubeQualityControl() != null){
          answers.add(result.getTubeQualityControl().toString());
        }else{answers.add("");}
        if(result.getTubeType() != null){
          answers.add(result.getTubeType().toString());
        }else{answers.add("");}
        if(result.getTubeMoment() != null){
          answers.add(result.getTubeMoment().toString());
        }else{answers.add("");}
        if(result.getTubeCollectionDate() != null){
          answers.add(result.getTubeCollectionDate());
        }else{answers.add("");}
        if(result.getTubeResponsible() != null){
          answers.add(result.getTubeResponsible());
        }else{
          answers.add("");
        }
        answers.add(result.getRecruitmentNumber().toString());
        /* information of aliquot */
        answers.add(result.getAliquotCode());
        answers.add(result.getAliquotName());
        answers.add(result.getAliquotProcessingDate());
        answers.add(result.getAliquotRegisterDate());
        answers.add(result.getAliquotResponsible());

        this.outputRecords.add(new ArrayList<>(answers));
      });
    });
  }

}
