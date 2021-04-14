package br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.factories;

import br.org.otus.laboratory.configuration.lot.receipt.MaterialReceiptCustomMetadata;
import br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.model.MaterialTrackingResultExtraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MaterialTrackingExtractionRecordsFactory {

  private final LinkedList<MaterialTrackingResultExtraction> inputRecords;
  private final List<List<Object>> outputRecords;
  private final List<MaterialReceiptCustomMetadata> customMetadata;

  public MaterialTrackingExtractionRecordsFactory(LinkedList<MaterialTrackingResultExtraction> records, List<MaterialReceiptCustomMetadata> customMetadata) {
    this.inputRecords = records;
    this.outputRecords = new LinkedList<>();
    this.customMetadata = customMetadata;
  }

  public List<List<Object>> getRecords() {
    return this.outputRecords;
  }

  public void buildResultInformation() {
    inputRecords.forEach(record -> {
      this.outputRecords.add(new ArrayList<>(this.createRecordsAnswers(record)));
    });
  }

  private List<String> createRecordsAnswers(MaterialTrackingResultExtraction record) {
    List<String> answers = new LinkedList<String>();
    answers.add(record.getMaterialCode().toString());
    answers.add(record.getOrigin().toString());
    answers.add(record.getDestination().toString());
    answers.add(record.getLotCode());
    answers.add(Boolean.toString(record.getReceipted()));
    answers.add(record.getSendingDate());
    answers.add(record.getReceiptDate());
    answers.add(record.getReceiveResponsible());

    if(record.getReceiptedMetadata() != null) {
      customMetadata.forEach(customMetadata -> {
        boolean contains = record.getReceiptedMetadata().contains(customMetadata.getId());
        if (contains) {
          answers.add("true");
        } else {
          answers.add("false");
        }
      });
    }


    answers.add(record.getOtherMetadata());

    return answers;
  }
}
