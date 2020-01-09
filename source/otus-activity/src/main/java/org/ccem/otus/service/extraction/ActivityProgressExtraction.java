package org.ccem.otus.service.extraction;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.extraction.factories.ActivityProgressHeadersFactory;
import org.ccem.otus.service.extraction.factories.ActivityProgressRecordsFactory;

import br.org.otus.api.Extractable;

public class ActivityProgressExtraction implements Extractable {

  private ActivityProgressHeadersFactory headersFactory;
  private ActivityProgressRecordsFactory recordsFactory;

  public ActivityProgressExtraction(ActivityProgressRecordsFactory progressRecords) {
    this.headersFactory = new ActivityProgressHeadersFactory();
    this.recordsFactory = progressRecords;
  }

  @Override
  public List<String> getHeaders() {
    return this.headersFactory.getHeaders();
  }

  @Override
  public List<List<Object>> getValues() throws DataNotFoundException {
    return this.recordsFactory.getRecords();
  }

}