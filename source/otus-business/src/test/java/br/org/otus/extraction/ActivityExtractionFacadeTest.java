package br.org.otus.extraction;

import br.org.otus.api.ExtractionService;
import br.org.otus.fileuploader.api.FileUploaderFacade;
import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.survey.api.SurveyFacade;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.service.extraction.ActivityProgressExtraction;
import org.ccem.otus.service.extraction.SurveyActivityExtraction;
import org.ccem.otus.service.extraction.model.ActivityExtraction;
import org.ccem.otus.service.extraction.model.ActivityExtractionActivityData;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivityExtractionFacade.class})
public class ActivityExtractionFacadeTest {

  private static final SurveyTemplate SURVEY_TEMPLATE = new SurveyTemplate();
  private static final String USER_EMAIL = "otus@otus.com";
  private static final String CENTER = "RS";
  private static final String ACRONYM = "ANTC";
  private static final Integer VERSION = 1;
  private static final String SURVEY_ID = "5e0658135b4ff40f8916d2b5";
  private static final String ACTIVITY_ID = "5e0658135b4ff40f8916d2b6";
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final ArrayList<SurveyForm> SURVEYS = new ArrayList<>();
  private static final String CSV_CONTENT = "{}";
  private static final byte[] CSV_BYTES = null;
  private static final String EXTRACTIONS_JSON = "[{}]";
  private static final int EXTRACTIONS_JSON_SIZE = 1;
  private static final byte[] BYTES = new byte[1];

  @InjectMocks
  private ActivityExtractionFacade activityExtractionFacade;
  @Mock
  private ActivityFacade activityFacade;
  @Mock
  private SurveyFacade surveyFacade;
  @Mock
  private FileUploaderFacade fileUploaderFacade;
  @Mock
  private ExtractionService extractionService;
  @Mock
  private ParticipantFacade participantFacade;

  @Mock
  private SurveyActivityExtraction surveyActivityExtraction;
  @Mock
  private ActivityProgressExtraction activityProgressExtraction;
  @Mock
  private ExtractionGatewayService extractionGatewayService;
  @Mock
  private GatewayResponse gatewayResponse;
  @Mock
  private ActivityExtraction activityExtraction;
  @Mock
  private ActivityExtractionActivityData activityExtractionActivityData;

  private SurveyForm surveyForm = PowerMockito.spy(new SurveyForm(SURVEY_TEMPLATE, USER_EMAIL));
  private SurveyActivity surveyActivity = PowerMockito.spy(new SurveyActivity());
  private Participant participant = PowerMockito.spy(new Participant(RECRUITMENT_NUMBER));

  @Before
  public void setUp() throws Exception {
    SURVEYS.add(surveyForm);

    surveyActivity.setStatusHistory(new ArrayList<>());
    surveyActivity.setActivityID(new ObjectId(ACTIVITY_ID));
    PowerMockito.when(surveyActivity.getSurveyForm()).thenReturn(surveyForm);

    PowerMockito.when(surveyForm.getSurveyID()).thenReturn(new ObjectId(SURVEY_ID));
    PowerMockito.when(surveyForm.getAcronym()).thenReturn(ACRONYM);
    PowerMockito.when(surveyForm.getVersion()).thenReturn(VERSION);

    PowerMockito.when(activityExtractionActivityData.getRecruitmentNumber()).thenReturn(RECRUITMENT_NUMBER);
    PowerMockito.when(activityExtraction.getActivityData()).thenReturn(activityExtractionActivityData);

    PowerMockito.when(surveyFacade.get(ACRONYM, VERSION)).thenReturn(surveyForm);
    PowerMockito.when(activityFacade.getExtraction(ACRONYM, VERSION)).thenReturn(new ArrayList<>());
    PowerMockito.when(activityFacade.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);

    PowerMockito.when(participantFacade.getByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);

    PowerMockito.whenNew(SurveyActivityExtraction.class).withAnyArguments().thenReturn(surveyActivityExtraction);
    PowerMockito.whenNew(ActivityProgressExtraction.class).withAnyArguments().thenReturn(activityProgressExtraction);
    PowerMockito.whenNew(ExtractionGatewayService.class).withNoArguments().thenReturn(extractionGatewayService);
    PowerMockito.whenNew(ActivityExtraction.class).withAnyArguments().thenReturn(activityExtraction);
  }

  @Test
  public void listSurveyVersions_method_should_call_listVersions_from_surveyFacade() {
    activityExtractionFacade.listSurveyVersions(ACRONYM);
    Mockito.verify(surveyFacade).listVersions(ACRONYM);
  }

  @Test
  public void createAttachmentsReportExtraction_method_should_call_getAttachmentsReport_from_extractionService() throws DataNotFoundException {
    activityExtractionFacade.createAttachmentsReportExtraction(ACRONYM, VERSION);
    Mockito.verify(extractionService).getAttachmentsReport(ACRONYM, VERSION);
  }

  @Test(expected = HttpResponseException.class)
  public void createAttachmentsReportExtraction_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(extractionService).getAttachmentsReport(ACRONYM, VERSION);
    activityExtractionFacade.createAttachmentsReportExtraction(ACRONYM, VERSION);
  }


//  @Test
//  public void createActivityProgressExtraction_method_should_call_methods_expected() throws DataNotFoundException {
//    when(extractionService.createExtraction(activityProgressExtraction)).thenReturn(BYTES);
//    activityExtractionFacade.createActivityProgressExtraction(CENTER);
//    Mockito.verify(activityFacade, Mockito.times(1)).getActivityProgressExtraction(CENTER);
//    Mockito.verify(extractionService, Mockito.times(1)).createExtraction(activityProgressExtraction);
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void createActivityProgressExtraction_method_should_handle_DataNotFoundException() throws DataNotFoundException {
//    doThrow(new DataNotFoundException()).when(extractionService).createExtraction(activityProgressExtraction);
//    activityExtractionFacade.createActivityProgressExtraction(CENTER);
//  }


  @Test
  public void downloadFiles_method_should_call_fileUploaderFacade_downloadFiles_method(){
    ArrayList<String> oids = new ArrayList<>();
    when(fileUploaderFacade.downloadFiles(oids)).thenReturn(BYTES);
    byte[] result = activityExtractionFacade.downloadFiles(oids);
    verify(fileUploaderFacade, Mockito.times(1)).downloadFiles(oids);
    assertEquals(BYTES, result);
  }

  @Test
  public void getSurveyActivitiesExtractionAsCsv_method_should_return_bytes_array() throws IOException {
    when(extractionGatewayService.getCsvSurveyExtraction(SURVEY_ID)).thenReturn(gatewayResponse);
    when(gatewayResponse.getData()).thenReturn(CSV_CONTENT);
    assertEquals(CSV_BYTES, activityExtractionFacade.getSurveyActivitiesExtractionAsCsv(ACRONYM, VERSION));
  }

  @Test
  public void getSurveyActivitiesExtractionAsJson_method_should_return_bytes_array() throws IOException {
    when(extractionGatewayService.getJsonSurveyExtraction(SURVEY_ID)).thenReturn(gatewayResponse);
    when(gatewayResponse.getData()).thenReturn(EXTRACTIONS_JSON);
    assertEquals(EXTRACTIONS_JSON_SIZE, activityExtractionFacade.getSurveyActivitiesExtractionAsJson(ACRONYM, VERSION).size());
  }

  @Test(expected = HttpResponseException.class)
  public void createExtractionFromPipeline_method_should_handle_MalformedURLException() throws IOException {
    doThrow(new MalformedURLException()).when(extractionGatewayService).getJsonSurveyExtraction(SURVEY_ID);
    activityExtractionFacade.getSurveyActivitiesExtractionAsJson(ACRONYM, VERSION);
  }


  @Test
  public void createActivityExtraction_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
    doReturn(true).when(surveyActivity).isFinalized();
    when(surveyActivity.couldBeExtracted()).thenReturn(true);
    doNothing().when(extractionGatewayService).createOrUpdateActivityExtraction(ACTIVITY_ID);
    activityExtractionFacade.createOrUpdateActivityExtraction(ACTIVITY_ID);
    verify(extractionGatewayService, Mockito.times(1)).createOrUpdateActivityExtraction(activityExtraction.toJson());
  }

//  @Test(expected = HttpResponseException.class)
  public void createActivityExtraction_method_should_handle_IOException() throws IOException {
    doThrow(new MalformedURLException()).when(extractionGatewayService).createOrUpdateActivityExtraction(ACTIVITY_ID);
    activityExtractionFacade.createOrUpdateActivityExtraction(ACTIVITY_ID);
  }


//  @Test
//  public void deleteActivityExtraction_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
//    doNothing().when(extractionGatewayService).deleteActivityExtraction(ACTIVITY_ID);
//    activityExtractionFacade.deleteActivityExtraction(ACTIVITY_ID);
//    verify(extractionGatewayService, Mockito.times(1)).deleteActivityExtraction(ACTIVITY_ID);
//    verifyLoggerInfoWasCalled();
//  }
//
//  @Test(expected = HttpResponseException.class)
//  public void deleteActivityExtraction_method_should_handle_IOException() throws IOException {
//    doThrow(new MalformedURLException()).when(extractionGatewayService).deleteActivityExtraction(ACTIVITY_ID);
//    activityExtractionFacade.deleteActivityExtraction(ACTIVITY_ID);
//    verifyLoggerSevereWasCalled();
//  }

}
