package org.ccem.otus.participant.business;

import br.org.otus.api.Extractable;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.business.extraction.factories.ParticipantExtractionHeadersFactory;
import org.ccem.otus.participant.business.extraction.factories.ParticipantExtractionRecordsFactory;
import org.ccem.otus.participant.business.extraction.model.ParticipantResultExtraction;

import java.util.LinkedList;
import java.util.List;

public class ParticipantExtraction implements Extractable {

  private ParticipantExtractionHeadersFactory headersFactory;
  private ParticipantExtractionRecordsFactory recordsFactory;

  public ParticipantExtraction(LinkedList<ParticipantResultExtraction> records) {
    this.headersFactory = new ParticipantExtractionHeadersFactory();
    this.recordsFactory = new ParticipantExtractionRecordsFactory(records);
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
