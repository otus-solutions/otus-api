package br.org.otus.extraction;

import static org.mockito.Mockito.*;

import br.org.otus.LoggerTestsParent;
import br.org.otus.api.ExtractionService;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.DataSourceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.examUploader.business.extraction.ExamUploadExtration;
import br.org.otus.laboratory.extraction.LaboratoryExtraction;
import br.org.otus.laboratory.participant.api.ParticipantLaboratoryFacade;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ExtractionFacade.class})
public class ExtractionFacadeTest extends LoggerTestsParent {

  @InjectMocks
  private ExtractionFacade extractionFacade;
  @Mock
  private ExamUploadFacade examUploadFacade;
  @Mock
  private ParticipantLaboratoryFacade participantLaboratoryFacade;
  @Mock
  private ExtractionService extractionService;
  @Mock
  private DataSourceService dataSourceService;

  @Mock
  private LaboratoryExtraction laboratoryExtraction;
  @Mock
  private ExamUploadExtration examUploadExtration;


  @Before
  public void setUp() throws Exception {
    setUpLogger(ExtractionFacade.class);
    PowerMockito.whenNew(ExamUploadExtration.class).withAnyArguments().thenReturn(examUploadExtration);
    PowerMockito.whenNew(LaboratoryExtraction.class).withAnyArguments().thenReturn(laboratoryExtraction);
  }


  @Test
  public void createLaboratoryExamsValuesExtraction_method_should_return_new_extraction_of_exam() throws Exception {
    extractionFacade.createLaboratoryExamsValuesExtraction();
    Mockito.verify(examUploadFacade).getExamResultsExtractionValues();
    Mockito.verify(extractionService).createExtraction(examUploadExtration);
  }

  @Test(expected = HttpResponseException.class)
  public void createLaboratoryExamsValuesExtraction_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(extractionService).createExtraction(examUploadExtration);
    extractionFacade.createLaboratoryExamsValuesExtraction();
  }


  @Test
  public void createLaboratoryExtraction_method_should_call_createExtraction_method_with_new_extraction() throws DataNotFoundException {
    extractionFacade.createLaboratoryExtraction();
    Mockito.verify(participantLaboratoryFacade).getLaboratoryExtraction();
    Mockito.verify(extractionService).createExtraction(laboratoryExtraction);
  }

  @Test(expected = HttpResponseException.class)
  public void createLaboratoryExtraction_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(extractionService).createExtraction(laboratoryExtraction);
    extractionFacade.createLaboratoryExtraction();
  }

}
