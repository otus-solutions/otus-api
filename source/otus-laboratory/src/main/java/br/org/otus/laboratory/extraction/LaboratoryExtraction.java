package br.org.otus.laboratory.extraction;

import java.util.LinkedList;
import java.util.List;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.api.Extractable;
import br.org.otus.laboratory.extraction.factories.LaboratoryExtractionHeadersFactory;
import br.org.otus.laboratory.extraction.factories.LaboratoryExtractionRecordsFactory;
import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;

public class LaboratoryExtraction implements Extractable {

  private final LaboratoryExtractionHeadersFactory headersFactory;
  private final LaboratoryExtractionRecordsFactory recordsFactory;

  public LaboratoryExtraction(LinkedList<LaboratoryRecordExtraction> extraction, List<TubeCustomMetadata> customMetadata) {
    this.headersFactory = new LaboratoryExtractionHeadersFactory(customMetadata);
    this.recordsFactory = new LaboratoryExtractionRecordsFactory(extraction, customMetadata);
  }

  @Override
  public List<String> getHeaders() {
    return this.headersFactory.getHeaders();
  }

  @Override
  public List<List<Object>> getValues() throws DataNotFoundException {
    this.recordsFactory.buildResultInformation();

    return this.recordsFactory.getRecords();
  }

}
