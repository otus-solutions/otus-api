package br.org.otus.examUploader.business.extraction.factories;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;

public class ExamUploadExtractionRecordsFactory {

  private LinkedHashMap<String, Object> correlation;
  private List<List<Object>> records;

  public ExamUploadExtractionRecordsFactory(LinkedHashSet<String> headers, LinkedHashSet<ParticipantExamUploadRecordExtraction> records) {
    this.correlation = new LinkedHashMap<>();
    for (Object header : headers) {
      this.correlation.put(header.toString(), "");
    }
  }

  public void buildResultInformation() {
    // TODO:
  }

  public List<List<Object>> getRecords() {
    return this.records;
  }

}
