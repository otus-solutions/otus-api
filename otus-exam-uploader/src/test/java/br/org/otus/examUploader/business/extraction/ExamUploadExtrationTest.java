package br.org.otus.examUploader.business.extraction;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.examUploader.business.extraction.factories.ExamUploadExtractionHeadersFactory;
import br.org.otus.examUploader.business.extraction.factories.ExamUploadExtractionRecordsFactory;

@RunWith(PowerMockRunner.class)
public class ExamUploadExtrationTest {

  @InjectMocks
  private ExamUploadExtration examUploadExtration;

  @Mock
  private ExamUploadExtractionHeadersFactory headersFactory;

  @Mock
  private ExamUploadExtractionRecordsFactory recordsFactory;

  @Before
  public void setUp() {
    PowerMockito.when(headersFactory.getHeaders()).thenReturn(new ArrayList<>());
  }

  @Test
  public void getHeaders_method_should_call_getHeaders_method() {
    assertNotNull(examUploadExtration);
    examUploadExtration.getHeaders();
    Mockito.verify(headersFactory, Mockito.times(1)).getHeaders();
  }

  @Test
  public void getValues_method_should_call_buildResultInformation_method() throws DataNotFoundException {
    assertNotNull(examUploadExtration);
    examUploadExtration.getValues();
    Mockito.verify(recordsFactory, Mockito.times(1)).buildResultInformation();
  }

  @Test
  public void getValues_method_should_call_getValues_method() throws DataNotFoundException {
    assertNotNull(examUploadExtration);
    examUploadExtration.getValues();
    Mockito.verify(recordsFactory, Mockito.times(1)).getValues();
  }

}
