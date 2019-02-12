package br.org.otus.laboratory.extraction;

import java.util.LinkedList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.api.Extractable;
import br.org.otus.laboratory.extraction.factories.LaboratoryExtractionHeadersFactory;
import br.org.otus.laboratory.extraction.factories.LaboratoryExtractionRecordsFactory;
import br.org.otus.laboratory.extraction.model.ParticipantLaboratoryRecordExtraction;

public class LaboratoryExtraction implements Extractable {

  private LaboratoryExtractionHeadersFactory headersFactory;
  private LaboratoryExtractionRecordsFactory recordsFactory;

  public LaboratoryExtraction(LinkedList<ParticipantLaboratoryRecordExtraction> tubes) {
    this.headersFactory = new LaboratoryExtractionHeadersFactory();
    this.recordsFactory = new LaboratoryExtractionRecordsFactory(this.headersFactory.getHeaders(), tubes);
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
