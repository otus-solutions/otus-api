package org.ccem.otus.service.extraction;

import org.ccem.otus.service.extraction.factories.ActivityProgressHeadersFactory;
import org.ccem.otus.service.extraction.factories.ActivityProgressRecordsFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivityProgressExtraction.class})
public class ActivityProgressExtractionTest {

  private ActivityProgressExtraction extraction;

  @Mock
  private ActivityProgressRecordsFactory records;
  @Mock
  private ActivityProgressHeadersFactory headers;

  @Before
  public void setup() throws Exception {
    PowerMockito.whenNew(ActivityProgressRecordsFactory.class).withAnyArguments().thenReturn(records);
    PowerMockito.whenNew(ActivityProgressHeadersFactory.class).withAnyArguments().thenReturn(headers);
    this.extraction = new ActivityProgressExtraction(records);
  }

  @Test
  public void should_method_getValues_constructor_values_for_extraction() throws Exception {
    this.extraction.getValues();

    Mockito.verify(records, Mockito.times(1)).getRecords();
  }

  @Test
  public void should_method_getHeaders_constructor_headers_for_extraction() throws Exception {
    this.extraction.getHeaders();

    Mockito.verify(headers, Mockito.times(1)).getHeaders();
  }

}