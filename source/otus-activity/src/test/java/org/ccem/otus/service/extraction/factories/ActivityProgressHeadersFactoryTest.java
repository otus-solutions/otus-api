package org.ccem.otus.service.extraction.factories;

import static org.junit.Assert.assertEquals;

import org.ccem.otus.service.extraction.enums.ActivityProgressExtractionHeaders;
import org.junit.Before;
import org.junit.Test;

public class ActivityProgressHeadersFactoryTest {

  private ActivityProgressHeadersFactory headers;

  @Before
  public void setup() {
    this.headers = new ActivityProgressHeadersFactory();
  }

  @Test
  public void should_initial_list_with_expected_size() {
    assertEquals(5, this.headers.getHeaders().size());
  }

  @Test
  public void getHeaders_method_should_return_list_with_values_expected() {
    assertEquals(ActivityProgressExtractionHeaders.RECRUITMENT_NUMBER.getValue(), this.headers.getHeaders().get(0));
    assertEquals(ActivityProgressExtractionHeaders.ACRONYM.getValue(), this.headers.getHeaders().get(1));
    assertEquals(ActivityProgressExtractionHeaders.STATUS.getValue(), this.headers.getHeaders().get(2));
    assertEquals(ActivityProgressExtractionHeaders.STATUS_DATE.getValue(), this.headers.getHeaders().get(3));
    assertEquals(ActivityProgressExtractionHeaders.INAPPLICABILITY_OBSERVATION.getValue(), this.headers.getHeaders().get(4));
  }

}