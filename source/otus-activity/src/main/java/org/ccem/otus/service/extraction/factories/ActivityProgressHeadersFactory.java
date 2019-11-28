package org.ccem.otus.service.extraction.factories;

import java.util.LinkedList;
import java.util.List;

import org.ccem.otus.service.extraction.enums.ActivityProgressExtractionHeaders;

public class ActivityProgressHeadersFactory {

  private List<String> headers;

  public ActivityProgressHeadersFactory() {
    this.headers = new LinkedList<String>();
    this.buildHeader();
  }

  public List<String> getHeaders() {
    return this.headers;
  }

  private void buildHeader() {
    this.headers.add(ActivityProgressExtractionHeaders.RECRUITMENT_NUMBER.getValue());
    this.headers.add(ActivityProgressExtractionHeaders.ACRONYM.getValue());
    this.headers.add(ActivityProgressExtractionHeaders.STATUS.getValue());
    this.headers.add(ActivityProgressExtractionHeaders.STATUS_DATE.getValue());
    this.headers.add(ActivityProgressExtractionHeaders.INAPPLICABILITY_OBSERVATION.getValue());
  }

}