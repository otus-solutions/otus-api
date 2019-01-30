package br.org.otus.examUploader.business.extraction;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;

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
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;

@RunWith(PowerMockRunner.class)
public class ExamUploadExtrationTest {

  @Mock
  private ExamUploadExtractionHeadersFactory headersFactory;

  @Mock
  private ExamUploadExtractionRecordsFactory recordsFactory;

  private LinkedHashSet<String> headers;
  private LinkedHashSet<ParticipantExamUploadRecordExtraction> records;

  @InjectMocks
  private ExamUploadExtration examUploadExtration = new ExamUploadExtration(headers, records);

  @Before
  public void setUp() {
    this.createFakeObjects();
    PowerMockito.when(headersFactory.getHeaders()).thenReturn(new ArrayList<>());
    PowerMockito.when(recordsFactory.getValues()).thenReturn(new ArrayList<>(new ArrayList<>()));
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

  private void createFakeObjects() {
    this.headers = new LinkedHashSet<>();
    headers.add("header_1");
    headers.add("header_2");
    
    ParticipantExamUploadRecordExtraction record = new ParticipantExamUploadRecordExtraction();
    
  }

}
