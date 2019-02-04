package br.org.otus.laboratory.extraction.factories;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class LaboratoryExtractionRecordsFactory {

  private List<String> headers;
  private LinkedHashSet<?> inputRecords;
  private List<List<Object>> outputRecords;

  public LaboratoryExtractionRecordsFactory(List<String> headers, LinkedHashSet<?> records) {
    this.headers = headers;
    this.inputRecords = records;
    this.outputRecords = new LinkedList<>();
  }

  public List<List<Object>> getRecords() {
    return this.outputRecords;
  }

  public void buildResultInformation() {
    // TODO:
  }

}
