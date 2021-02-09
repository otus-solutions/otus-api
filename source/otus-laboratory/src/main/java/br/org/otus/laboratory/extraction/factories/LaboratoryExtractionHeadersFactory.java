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
    this.headers.add(LaboratoryExtractionHeaders.UNATTACHED_IDENTIFICATION.getValue());
    /* headers information of tube */
    this.headers.add(LaboratoryExtractionHeaders.TUBE_CODE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.TUBE_QUALITY_CONTROL.getValue());
    this.headers.add(LaboratoryExtractionHeaders.TUBE_TYPE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.TUBE_MOMENT.getValue());
    this.headers.add(LaboratoryExtractionHeaders.TUBE_COLLECTION_DATE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.TUBE_RESPONSIBLE.getValue());
    /* headers information of aliquot */
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_CODE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_NAME.getValue());
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_CONTAINER.getValue());
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_PROCESSING_DATE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_REGISTER_DATE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_RESPONSIBLE.getValue());
    this.headers.add(LaboratoryExtractionHeaders.ALIQUOT_ROLE.getValue());
  }

}
