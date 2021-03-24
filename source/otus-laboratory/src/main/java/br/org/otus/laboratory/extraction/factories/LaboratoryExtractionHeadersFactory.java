package br.org.otus.laboratory.extraction.factories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import br.org.otus.laboratory.extraction.enums.LaboratoryExtractionHeaders;
import org.ccem.otus.service.extraction.enums.SurveyActivityExtractionHeaders;
import org.ccem.otus.survey.template.item.questions.Question;

public class LaboratoryExtractionHeadersFactory {

  private final List<String> headers;
  private final List<TubeCustomMetadata> customMetadata;

  public LaboratoryExtractionHeadersFactory(List<TubeCustomMetadata> customMetadata) {
    this.customMetadata = customMetadata;
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

    this.customMetadata.forEach(customMetadata -> {
      this.headers.add(customMetadata.getExtractionValue());
    });

  }

}
