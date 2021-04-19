package br.org.otus.laboratory.project.transportation.business.extraction.materialTracking;

import br.org.otus.api.Extractable;
import br.org.otus.laboratory.configuration.lot.receipt.MaterialReceiptCustomMetadata;
import br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.factories.MaterialTrackingExtractionHeadersFactory;
import br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.factories.MaterialTrackingExtractionRecordsFactory;
import br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.model.MaterialTrackingResultExtraction;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.LinkedList;
import java.util.List;

public class MaterialTrackingExtraction implements Extractable {

  private final MaterialTrackingExtractionHeadersFactory headersFactory;
  private final MaterialTrackingExtractionRecordsFactory recordsFactory;

  public MaterialTrackingExtraction(LinkedList<MaterialTrackingResultExtraction> extraction, List<MaterialReceiptCustomMetadata> customMetadata) {
    this.headersFactory = new MaterialTrackingExtractionHeadersFactory(customMetadata);
    this.recordsFactory = new MaterialTrackingExtractionRecordsFactory(extraction, customMetadata);
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
