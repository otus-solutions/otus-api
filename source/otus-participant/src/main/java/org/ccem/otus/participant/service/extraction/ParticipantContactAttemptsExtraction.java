package org.ccem.otus.participant.service.extraction;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.service.extraction.factories.ParticipantContactAttemptsHeadersFactory;
import org.ccem.otus.participant.service.extraction.factories.ParticipantContactAttemptsRecordsFactory;

import br.org.otus.api.Extractable;

public class ParticipantContactAttemptsExtraction implements Extractable {

  private ParticipantContactAttemptsHeadersFactory headersFactory;
  private ParticipantContactAttemptsRecordsFactory recordsFactory;

  public ParticipantContactAttemptsExtraction(ArrayList<ParticipantContactAttempt> records) {
    this.headersFactory = new ParticipantContactAttemptsHeadersFactory();
    this.recordsFactory = new ParticipantContactAttemptsRecordsFactory(records);
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