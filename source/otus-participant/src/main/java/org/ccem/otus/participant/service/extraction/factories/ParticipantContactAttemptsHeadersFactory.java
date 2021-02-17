package org.ccem.otus.participant.service.extraction.factories;

import java.util.LinkedList;
import java.util.List;

import org.ccem.otus.participant.service.extraction.enums.ParticipantContactAttemptsExtractionHeaders;

public class ParticipantContactAttemptsHeadersFactory {

  private List<String> headers;

  public ParticipantContactAttemptsHeadersFactory() {
    this.headers = new LinkedList<String>();
    this.buildHeader();
  }

  public List<String> getHeaders() {
    return this.headers;
  }

  private void buildHeader() {
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.RECRUITMENT_NUMBER.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.USER.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.STATUS.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.DATE.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.ADDRESS_STREET.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.ADDRESS_NUMBER.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.ADDRESS_ZIP_CODE.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.ADDRESS_COMPLEMENT.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.ADDRESS_DISTRICT.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.ADDRESS_CITY.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.ADDRESS_STATE.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.ADDRESS_COUNTRY.getValue());
    this.headers.add(ParticipantContactAttemptsExtractionHeaders.SECTOR_IBGE.getValue());
  }

}