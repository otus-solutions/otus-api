package br.org.otus.examUploader.business.extraction;

import java.util.LinkedHashSet;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.org.otus.examUploader.business.extraction.factories.ExamUploadExtractionHeadersFactory;
import br.org.otus.examUploader.business.extraction.factories.ExamUploadExtractionRecordsFactory;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;

@RunWith(PowerMockRunner.class)
public class ExamUploadExtrationTest {

  @InjectMocks
  private ExamUploadExtration examUploadExtration;
  @Mock
  private ExamUploadExtractionHeadersFactory headersFactory;
  @Mock
  private ExamUploadExtractionRecordsFactory recordsFactory;
  @Mock
  private LinkedHashSet<String> headers;
  @Mock
  private LinkedHashSet<ParticipantExamUploadRecordExtraction> records;

  @Before
  public void setup() {
    Whitebox.setInternalState(examUploadExtration, "headersFactory", headersFactory);
    Whitebox.setInternalState(examUploadExtration, "recordsFactory", recordsFactory);
  }

  @Test
  public void getHeaders_method_should_call_getHeaders_method() {
    examUploadExtration.getHeaders();
    Mockito.verify(headersFactory, Mockito.times(1)).getHeaders();
  }

  @Test
  public void getValues_method_should_call_buildResultInformation_method() throws DataNotFoundException {
    examUploadExtration.getValues();
    Mockito.verify(recordsFactory, Mockito.times(1)).buildResultInformation();
  }

  @Test
  public void getValues_method_should_call_getValues_method() throws DataNotFoundException {
    examUploadExtration.getValues();
    Mockito.verify(recordsFactory, Mockito.times(1)).getRecords();
  }

}
