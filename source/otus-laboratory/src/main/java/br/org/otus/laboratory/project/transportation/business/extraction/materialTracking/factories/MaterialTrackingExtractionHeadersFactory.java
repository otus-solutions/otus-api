package br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.factories;

import br.org.otus.laboratory.configuration.lot.receipt.MaterialReceiptCustomMetadata;
import br.org.otus.laboratory.project.transportation.business.extraction.materialTracking.enums.MaterialTrackingExtractionHeaders;

import java.util.LinkedList;
import java.util.List;

public class MaterialTrackingExtractionHeadersFactory {

  private final List<String> headers;
  private final List<MaterialReceiptCustomMetadata> customMetadata;

  public MaterialTrackingExtractionHeadersFactory(List<MaterialReceiptCustomMetadata> customMetadata) {
    this.headers = new LinkedList<String>();
    this.customMetadata = customMetadata;
    this.buildHeader();
  }

  public List<String> getHeaders() {
    return this.headers;
  }

  private void buildHeader() {
    this.headers.add(MaterialTrackingExtractionHeaders.MATERIAL_CODE.getValue());
    this.headers.add(MaterialTrackingExtractionHeaders.ORIGIN.getValue());
    this.headers.add(MaterialTrackingExtractionHeaders.DESTINATION.getValue());
    this.headers.add(MaterialTrackingExtractionHeaders.LOT_ID.getValue());
    this.headers.add(MaterialTrackingExtractionHeaders.RECEIPTED.getValue());
    this.headers.add(MaterialTrackingExtractionHeaders.SENDING_DATE.getValue());
    this.headers.add(MaterialTrackingExtractionHeaders.RECEIPT_DATE.getValue());
    this.headers.add(MaterialTrackingExtractionHeaders.RECEIVE_RESPONSIBLE.getValue());

    this.customMetadata.forEach(customMetadata -> {
      this.headers.add(customMetadata.getExtractionValue());
    });

    this.headers.add(MaterialTrackingExtractionHeaders.OTHER_METADATA.getValue());

  }

}
