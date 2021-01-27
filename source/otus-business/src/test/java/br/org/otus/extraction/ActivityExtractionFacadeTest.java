package br.org.otus.extraction;

import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.service.extraction.ActivityProgressExtraction;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;
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
public class ActivityExtractionFacadeTest {

  private static final SurveyTemplate SURVEY_TEMPLATE = new SurveyTemplate();
  private static final String USER_EMAIL = "otus@otus.com";
  private static final String ACRONYM = "ANTC";
  private static final Integer VERSION = 1;
  private static final ArrayList<SurveyForm> SURVEYS = new ArrayList<>();
  private static final String EXTRACTIONS_JSON = "{}";
  private static final String SURVEY_ID = "5e0658135b4ff40f8916d2b5";
  private static final String ACTIVITY_ID = "5e0658135b4ff40f8916d2b6";
  private static final byte[] BYTES = new byte[1];

  @InjectMocks
  private ActivityExtractionFacade activityExtractionFacade;
  @Mock
  private ActivityFacade activityFacade;
  @Mock
  private SurveyFacade surveyFacade;
  @Mock
  private SurveyActivityExtraction surveyActivityExtraction;
  @Mock
  private ActivityProgressExtraction activityProgressExtraction;
  @Mock
  private ExtractionGatewayService extractionGatewayService;
  @Mock
  private GatewayResponse gatewayResponse;

  private SurveyForm surveyForm = PowerMockito.spy(new SurveyForm(SURVEY_TEMPLATE, USER_EMAIL));
  private SurveyActivity surveyActivity = PowerMockito.spy(new SurveyActivity());

  @Before
  public void setUp() throws Exception {
    SURVEYS.add(surveyForm);
    PowerMockito.when(surveyForm.getSurveyID()).thenReturn(new ObjectId(SURVEY_ID));
    PowerMockito.when(surveyFacade.get(ACRONYM, VERSION)).thenReturn(SURVEYS.get(0));
    PowerMockito.when(activityFacade.getExtraction(ACRONYM, VERSION)).thenReturn(new ArrayList<>());
    PowerMockito.when(activityFacade.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);
    PowerMockito.whenNew(SurveyActivityExtraction.class).withAnyArguments().thenReturn(surveyActivityExtraction);
    PowerMockito.whenNew(ActivityProgressExtraction.class).withAnyArguments().thenReturn(activityProgressExtraction);
    PowerMockito.whenNew(ExtractionGatewayService.class).withNoArguments().thenReturn(extractionGatewayService);
  }

//  @Test
  public void getSurveyActivitiesExtractionAsCsv_method_should_return_bytes_array() throws IOException {
    when(extractionGatewayService.getJsonSurveyExtraction(SURVEY_ID)).thenReturn(gatewayResponse);
    when(gatewayResponse.getData()).thenReturn(BYTES);
    assertEquals(BYTES, activityExtractionFacade.getSurveyActivitiesExtractionAsCsv(ACRONYM, VERSION));
  }


//  @Test
  public void getSurveyActivitiesExtractionAsJson_method_should_return_bytes_array() throws IOException {
    when(extractionGatewayService.getJsonSurveyExtraction(SURVEY_ID)).thenReturn(gatewayResponse);
    when(gatewayResponse.getData()).thenReturn(EXTRACTIONS_JSON);
    assertEquals(EXTRACTIONS_JSON, activityExtractionFacade.getSurveyActivitiesExtractionAsJson(ACRONYM, VERSION));
  }

  @Test(expected = HttpResponseException.class)
  public void createExtractionFromPipeline_method_should_handle_MalformedURLException() throws IOException {
    doThrow(new MalformedURLException()).when(extractionGatewayService).getJsonSurveyExtraction(SURVEY_ID);
    activityExtractionFacade.getSurveyActivitiesExtractionAsJson(ACRONYM, VERSION);
  }


//  @Test
  public void createActivityExtraction_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
    doNothing().when(extractionGatewayService).createOrUpdateActivityExtraction(ACTIVITY_ID);
    activityExtractionFacade.createOrUpdateActivityExtraction(ACTIVITY_ID);
    verify(extractionGatewayService, Mockito.times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
  }

//  @Test(expected = HttpResponseException.class)
  public void createActivityExtraction_method_should_handle_IOException() throws IOException {
    doThrow(new MalformedURLException()).when(extractionGatewayService).createOrUpdateActivityExtraction(ACTIVITY_ID);
    activityExtractionFacade.createOrUpdateActivityExtraction(ACTIVITY_ID);
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
