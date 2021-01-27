package br.org.otus.extraction;

import br.org.otus.LoggerTestsParent;
import br.org.otus.api.ExtractionService;
import br.org.otus.examUploader.api.ExamUploadFacade;
import br.org.otus.examUploader.business.extraction.ExamUploadExtration;
import br.org.otus.fileuploader.api.FileUploaderFacade;
import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.laboratory.extraction.LaboratoryExtraction;
import br.org.otus.laboratory.participant.api.ParticipantLaboratoryFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ExtractionFacade.class})
public class ActivityExtractionFacadeTest extends LoggerTestsParent {

  private static final SurveyTemplate SURVEY_TEMPLATE = new SurveyTemplate();
  private static final String USER_EMAIL = "otus@otus.com";
  private static final String CENTER = "RS";
  private static final String ACRONYM = "ANTC";
  private static final Integer VERSION = 1;
  private static final ArrayList<SurveyForm> SURVEYS = new ArrayList<>();
  private static final byte[] BYTES = new byte[1];
  private static final String PIPELINE_NAME = "pipeline";
  private static final String ACTIVITY_ID = "12345";

  @InjectMocks
  private ActivityExtractionFacade extractionFacade;
  @Mock
  private ActivityFacade activityFacade;
  @Mock
  private SurveyFacade surveyFacade;
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
  @Mock
  private GatewayResponse gatewayResponse;

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


  //@Test
  public void createExtractionFromPipeline_method_should_return_bytes_array() throws IOException {
    when(extractionGatewayService.getPipelineJsonExtraction(PIPELINE_NAME)).thenReturn(gatewayResponse);
    when(gatewayResponse.getData()).thenReturn(BYTES);
    assertEquals(BYTES, extractionFacade.createJsonExtractionFromPipeline(PIPELINE_NAME));
  }

  @Test(expected = HttpResponseException.class)
  public void createExtractionFromPipeline_method_should_handle_MalformedURLException() throws IOException {
    doThrow(new MalformedURLException()).when(extractionGatewayService).getPipelineJsonExtraction(PIPELINE_NAME);
    extractionFacade.createJsonExtractionFromPipeline(PIPELINE_NAME);
  }


  //@Test
  public void createActivityExtraction_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
    doNothing().when(extractionGatewayService).createOrUpdateActivityExtraction(ACTIVITY_ID);
    extractionFacade.createOrUpdateActivityExtraction(ACTIVITY_ID);
    verify(extractionGatewayService, Mockito.times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
    verifyLoggerInfoWasCalled();
  }

  //@Test(expected = HttpResponseException.class)
  public void createActivityExtraction_method_should_handle_IOException() throws IOException {
    doThrow(new MalformedURLException()).when(extractionGatewayService).createOrUpdateActivityExtraction(ACTIVITY_ID);
    extractionFacade.createOrUpdateActivityExtraction(ACTIVITY_ID);
    verifyLoggerSevereWasCalled();
  }


//  @Test
//  public void deleteActivityExtraction_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
//    doNothing().when(extractionGatewayService).deleteActivityExtraction(ACTIVITY_ID);
//    extractionFacade.deleteActivityExtraction(ACTIVITY_ID);
//    verify(extractionGatewayService, Mockito.times(1)).deleteActivityExtraction(ACTIVITY_ID);
//    verifyLoggerInfoWasCalled();
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void deleteActivityExtraction_method_should_handle_IOException() throws IOException {
//    doThrow(new MalformedURLException()).when(extractionGatewayService).deleteActivityExtraction(ACTIVITY_ID);
//    extractionFacade.deleteActivityExtraction(ACTIVITY_ID);
//    verifyLoggerSevereWasCalled();
//  }

}
