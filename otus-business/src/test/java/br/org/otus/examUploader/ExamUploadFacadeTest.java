package br.org.otus.examUploader;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.examUploader.business.ExamUploadService;

@RunWith(PowerMockRunner.class)
public class ExamUploadFacadeTest {

  @InjectMocks
  private ExamUploadFacade facade;

  @Mock
  private ExamUploadService examUploadService;

  @Test
  public void getExamResultsExtractionHeader_should_call_getExamResultsExtractionHeader_method()
      throws DataNotFoundException {
    facade.getExamResultsExtractionHeader();
    Mockito.verify(examUploadService, Mockito.times(1)).getExamResultsExtractionHeader();
  }

  @Test
  public void getExamResultsExtractionValues_should_call_getExamResultsExtractionValues_method()
      throws DataNotFoundException {
    facade.getExamResultsExtractionValues();
    Mockito.verify(examUploadService, Mockito.times(1)).getExamResultsExtractionValues();
  }

}
