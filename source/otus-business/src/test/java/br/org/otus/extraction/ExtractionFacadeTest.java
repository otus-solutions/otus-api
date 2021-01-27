package br.org.otus.extraction;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import br.org.otus.LoggerTestsParent;
import br.org.otus.api.ExtractionService;
import br.org.otus.fileuploader.api.FileUploaderFacade;
import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.service.DataSourceService;
import org.ccem.otus.service.extraction.ActivityProgressExtraction;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;
import org.ccem.otus.service.extraction.preprocessing.AutocompleteQuestionPreProcessor;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.SurveyTemplate;
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
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ExtractionFacade.class})
public class ExtractionFacadeTest extends LoggerTestsParent {

  private static final SurveyTemplate SURVEY_TEMPLATE = new SurveyTemplate();
  private static final String USER_EMAIL = "otus@otus.com";
  private static final String CENTER = "RS";
  private static final String ACRONYM = "ANTC";
  private static final Integer VERSION = 1;
  private static final ArrayList<SurveyForm> SURVEYS = new ArrayList<>();
  private static final byte[] BYTES = new byte[1];

  @InjectMocks
  private ExtractionFacade extractionFacade;
  @Mock
  private ActivityFacade activityFacade;
  @Mock
  private SurveyFacade surveyFacade;
  @Mock
  private ExamUploadFacade examUploadFacade;
  @Mock
  private AutocompleteQuestionPreProcessor autocompleteQuestionPreProcessor;
  @Mock
  private ParticipantLaboratoryFacade participantLaboratoryFacade;
  @Mock
  private FileUploaderFacade fileUploaderFacade;
  @Mock
  private ExtractionService extractionService;
  @Mock
  private DataSourceService dataSourceService;

  @Mock
  private LaboratoryExtraction laboratoryExtraction;
  @Mock
  private SurveyActivityExtraction surveyActivityExtraction;
  @Mock
  private ExamUploadExtration examUploadExtration;
  @Mock
  private ActivityProgressExtraction activityProgressExtraction;
  @Mock
  private ExtractionGatewayService extractionGatewayService;

  private SurveyForm surveyForm = new SurveyForm(SURVEY_TEMPLATE, USER_EMAIL);

  @Before
  public void setUp() throws Exception {
    setUpLogger(ExtractionFacade.class);
    SURVEYS.add(surveyForm);
    SURVEYS.add(surveyForm);
    PowerMockito.when(surveyFacade.get(ACRONYM, VERSION)).thenReturn(SURVEYS.get(0));
    PowerMockito.when(activityFacade.getExtraction(ACRONYM, VERSION)).thenReturn(new ArrayList<>());
    PowerMockito.whenNew(SurveyActivityExtraction.class).withAnyArguments().thenReturn(surveyActivityExtraction);
    PowerMockito.whenNew(ExamUploadExtration.class).withAnyArguments().thenReturn(examUploadExtration);
    PowerMockito.whenNew(LaboratoryExtraction.class).withAnyArguments().thenReturn(laboratoryExtraction);
    PowerMockito.whenNew(ActivityProgressExtraction.class).withAnyArguments().thenReturn(activityProgressExtraction);
    PowerMockito.whenNew(ExtractionGatewayService.class).withNoArguments().thenReturn(extractionGatewayService);
  }

  @Test
  public void createActivityExtraction_method_should_return_new_extraction_of_activities() throws Exception {
    extractionFacade.createActivityExtraction(ACRONYM, VERSION);
    Mockito.verify(activityFacade).getExtraction(ACRONYM, VERSION);
    Mockito.verify(surveyFacade).get(ACRONYM, VERSION);
    Mockito.verify(surveyActivityExtraction, Mockito.times(1)).addPreProcessor(autocompleteQuestionPreProcessor);
    Mockito.verify(extractionService).createExtraction(surveyActivityExtraction);
  }

  @Test(expected = HttpResponseException.class)
  public void createActivityExtraction_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(extractionService).createExtraction(surveyActivityExtraction);
    extractionFacade.createActivityExtraction(ACRONYM, VERSION);
  }

  @Test
  public void listSurveyVersions_method_should_call_listVersions_from_surveyFacade() {
    extractionFacade.listSurveyVersions(ACRONYM);
    Mockito.verify(surveyFacade).listVersions(ACRONYM);
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


  @Test
  public void createAttachmentsReportExtraction_method_should_call_getAttachmentsReport_from_extractionService() throws DataNotFoundException {
    extractionFacade.createAttachmentsReportExtraction(ACRONYM, VERSION);
    Mockito.verify(extractionService).getAttachmentsReport(ACRONYM, VERSION);
  }

  @Test(expected = HttpResponseException.class)
  public void createAttachmentsReportExtraction_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(extractionService).getAttachmentsReport(ACRONYM, VERSION);
    extractionFacade.createAttachmentsReportExtraction(ACRONYM, VERSION);
  }


  @Test
  public void createActivityProgressExtraction_method_should_call_methods_expected() throws DataNotFoundException {
    extractionFacade.createActivityProgressExtraction(CENTER);
    Mockito.verify(activityFacade, Mockito.times(1)).getActivityProgressExtraction(CENTER);
    Mockito.verify(extractionService, Mockito.times(1)).createExtraction(activityProgressExtraction);
  }

  @Test(expected = HttpResponseException.class)
  public void createActivityProgressExtraction_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(extractionService).createExtraction(activityProgressExtraction);
    extractionFacade.createActivityProgressExtraction(CENTER);
  }


  @Test
  public void downloadFiles_method_should_call_fileUploaderFacade_downloadFiles_method(){
    ArrayList<String> oids = new ArrayList<>();
    when(fileUploaderFacade.downloadFiles(oids)).thenReturn(BYTES);
    byte[] result = extractionFacade.downloadFiles(oids);
    verify(fileUploaderFacade, Mockito.times(1)).downloadFiles(oids);
    assertEquals(BYTES, result);
  }

}
