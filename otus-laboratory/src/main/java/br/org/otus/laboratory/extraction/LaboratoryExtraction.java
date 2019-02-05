package br.org.otus.laboratory.extraction;

import java.util.LinkedHashSet;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.api.Extractable;
import br.org.otus.laboratory.extraction.factories.LaboratoryExtractionHeadersFactory;
import br.org.otus.laboratory.extraction.factories.LaboratoryExtractionRecordsFactory;

public class LaboratoryExtraction implements Extractable {

  private LaboratoryExtractionHeadersFactory headers;
  private LaboratoryExtractionRecordsFactory records;

  public LaboratoryExtraction() {
    this.headers = new LaboratoryExtractionHeadersFactory();
    this.records = new LaboratoryExtractionRecordsFactory(this.headers.getHeaders(), null);
  }

  @Override
  public LinkedHashSet<String> getHeaders() {
    return null;
  }

  @Override
  public List<List<Object>> getValues() throws DataNotFoundException {
    return null;
  }

}
