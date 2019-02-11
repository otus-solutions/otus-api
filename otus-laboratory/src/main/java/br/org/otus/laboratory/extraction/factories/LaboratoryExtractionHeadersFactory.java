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
    /* headers information of tube */
    this.headers.add(LaboratoryExtractionHeaders.TUBE_CODE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.QUALITY_CONTROL.getValue());
    this.headers.add(LaboratoryExtractionHeaders.TUBE_NAME.getValue());
    this.headers.add(LaboratoryExtractionHeaders.COLLECTION_DATE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.RESPONSIBLE.getValue());
    /* headers information of aliquot */
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_CODE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_NAME.getValue());
    this.headers.add(LaboratoryExtractionHeaders.PROCESSING_DATE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.REGISTER_DATE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.RESPONSIBLE.getValue());
  }

}
