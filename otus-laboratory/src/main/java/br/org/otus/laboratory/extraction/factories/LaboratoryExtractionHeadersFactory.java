package br.org.otus.laboratory.extraction.factories;

import java.util.LinkedList;
import java.util.List;

import br.org.otus.laboratory.extraction.enums.LaboratoryExtractionHeaders;

public class LaboratoryExtractionHeadersFactory {

  private List<String> headers;

  public LaboratoryExtractionHeadersFactory() {
    this.headers = new LinkedList<String>();
    this.buildHeader();
  }

  public List<String> getHeaders() {
    return this.headers;
  }

  private void buildHeader() {
    this.headers.add(LaboratoryExtractionHeaders.RECRUITMENT_NUMBER.getValue());
    this.headers.add(LaboratoryExtractionHeaders.TUBE_CODE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.QUALITY_CONTROL.getValue());
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_CODE.getValue());
  }

}
