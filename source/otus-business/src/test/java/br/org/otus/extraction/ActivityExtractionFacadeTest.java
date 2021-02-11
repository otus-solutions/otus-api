package br.org.otus.extraction;

import br.org.otus.api.CsvExtraction;
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
import org.ccem.otus.service.extraction.model.ActivityExtractionSurveyData;
import org.ccem.otus.service.extraction.model.SurveyExtraction;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ActivityExtractionFacade.class, SurveyExtraction.class})
public class ActivityExtractionFacadeTest {

  private static final SurveyTemplate SURVEY_TEMPLATE = new SurveyTemplate();
  private static final String USER_EMAIL = "otus@otus.com";
  private static final String CENTER = "RS";
  private static final String ACRONYM = "ANTC";
  private static final Integer VERSION = 1;
  private static final String SURVEY_ID = "5e0658135b4ff40f8916d2b5";
  private static final String ACTIVITY_ID = "5e0658135b4ff40f8916d2b6";
  private static final String ACTIVITY_ID_WITHOUT_EXTRACTION = "5e0658135b4ff40f8916d2b7";
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final ArrayList<SurveyForm> SURVEYS = new ArrayList<>();
  private static final byte[] CSV_BYTES = null;
  private static final String EXTRACTIONS_JSON = "[{}]";
  private static final int EXTRACTIONS_JSON_SIZE = 1;
  private static final byte[] BYTES = new byte[1];
  private static final String SURVEY_EXTRACTION_JSON = "{}";
  private static final String R_SCRIPT_JSON_RESULT = "{}";
  private static final String CSV_JSON = "{header: [\"x\"], values: [1]}";

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
  @Mock
  private ActivityExtractionSurveyData activityExtractionSurveyData;

  @Mock
  private SurveyActivity surveyActivity;
  @Mock
  private SurveyExtraction surveyExtraction;
  @Mock
  private CsvExtraction csvExtraction;
  private SurveyForm surveyForm = PowerMockito.spy(new SurveyForm(SURVEY_TEMPLATE, USER_EMAIL));
  private Participant participant = PowerMockito.spy(new Participant(RECRUITMENT_NUMBER));

  @Before
  public void setUp() throws Exception {
    SURVEYS.add(surveyForm);

    surveyActivity.setStatusHistory(new ArrayList<>());
    surveyActivity.setActivityID(new ObjectId(ACTIVITY_ID));

    PowerMockito.when(surveyForm.getSurveyID()).thenReturn(new ObjectId(SURVEY_ID));
    PowerMockito.when(surveyForm.getAcronym()).thenReturn(ACRONYM);
    PowerMockito.when(surveyForm.getVersion()).thenReturn(VERSION);

    PowerMockito.when(surveyActivity.getSurveyForm()).thenReturn(surveyForm);
    PowerMockito.when(surveyFacade.get(ACRONYM, VERSION)).thenReturn(surveyForm);

    PowerMockito.when(activityExtractionActivityData.getRecruitmentNumber()).thenReturn(RECRUITMENT_NUMBER);
    PowerMockito.when(activityExtractionActivityData.getId()).thenReturn(ACTIVITY_ID);
    PowerMockito.when(activityExtractionSurveyData.getId()).thenReturn(SURVEY_ID);

    PowerMockito.when(activityExtraction.getSurveyData()).thenReturn(activityExtractionSurveyData);
    PowerMockito.when(activityExtraction.getActivityData()).thenReturn(activityExtractionActivityData);

    PowerMockito.when(activityFacade.getExtraction(ACRONYM, VERSION)).thenReturn(new ArrayList<>());
    PowerMockito.when(activityFacade.getByID(ACTIVITY_ID)).thenReturn(surveyActivity);

    PowerMockito.when(participantFacade.getByRecruitmentNumber(RECRUITMENT_NUMBER)).thenReturn(participant);

    PowerMockito.whenNew(SurveyActivityExtraction.class).withAnyArguments().thenReturn(surveyActivityExtraction);
    PowerMockito.whenNew(ActivityProgressExtraction.class).withAnyArguments().thenReturn(activityProgressExtraction);
    PowerMockito.whenNew(ExtractionGatewayService.class).withNoArguments().thenReturn(extractionGatewayService);
    PowerMockito.whenNew(ActivityExtraction.class).withAnyArguments().thenReturn(activityExtraction);
    PowerMockito.whenNew(CsvExtraction.class).withAnyArguments().thenReturn(csvExtraction);

    when(surveyExtraction.getSurveyAcronym()).thenReturn(ACRONYM);
    when(surveyExtraction.getSurveyVersion()).thenReturn(VERSION);
    PowerMockito.mockStatic(SurveyExtraction.class);
    when(SurveyExtraction.class, "fromJson", SURVEY_EXTRACTION_JSON).thenReturn(surveyExtraction);
    when(extractionService.createExtraction(csvExtraction)).thenReturn(BYTES);
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

  @Test
  public void createActivityProgressExtraction_method_should_call_methods_expected() throws DataNotFoundException {
    when(extractionService.createExtraction(activityProgressExtraction)).thenReturn(BYTES);
    activityExtractionFacade.createActivityProgressExtraction(CENTER);
    Mockito.verify(activityFacade, Mockito.times(1)).getActivityProgressExtraction(CENTER);
    Mockito.verify(extractionService, Mockito.times(1)).createExtraction(activityProgressExtraction);
  }

  @Test(expected = HttpResponseException.class)
  public void createActivityProgressExtraction_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException()).when(extractionService).createExtraction(activityProgressExtraction);
    activityExtractionFacade.createActivityProgressExtraction(CENTER);
  }

  @Test
  public void downloadFiles_method_should_call_fileUploaderFacade_downloadFiles_method(){
    ArrayList<String> oids = new ArrayList<>();
    when(fileUploaderFacade.downloadFiles(oids)).thenReturn(BYTES);
    byte[] result = activityExtractionFacade.downloadFiles(oids);
    verify(fileUploaderFacade, Mockito.times(1)).downloadFiles(oids);
    assertEquals(BYTES, result);
  }

  @Test
  public void createOrUpdateActivityExtraction_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
    doReturn(true).when(surveyActivity).isFinalized();
    when(surveyActivity.couldBeExtracted()).thenReturn(true);
    doNothing().when(extractionGatewayService).createOrUpdateActivityExtraction(ACTIVITY_ID);
    activityExtractionFacade.createOrUpdateActivityExtraction(ACTIVITY_ID);
    verify(extractionGatewayService, Mockito.times(1)).createOrUpdateActivityExtraction(activityExtraction.toJson());
  }

  @Test(expected = HttpResponseException.class)
  public void createOrUpdateActivityExtraction_method_should_handle_ValidationException_in_case_discarded_activity() {
    when(surveyActivity.isDiscarded()).thenReturn(true);
    activityExtractionFacade.createOrUpdateActivityExtraction(ACTIVITY_ID);
  }

  @Test(expected = HttpResponseException.class)
  public void createOrUpdateActivityExtraction_method_should_handle_ValidationException_in_case_activity_unextractable() {
    when(surveyActivity.isDiscarded()).thenReturn(false);
    when(surveyActivity.couldBeExtracted()).thenReturn(false);
    activityExtractionFacade.createOrUpdateActivityExtraction(ACTIVITY_ID);
  }

  @Test
  public void deleteActivityExtraction_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
    when(surveyActivity.isDiscarded()).thenReturn(false);
    when(surveyActivity.couldBeExtracted()).thenReturn(true);
    activityExtractionFacade.deleteActivityExtraction(ACTIVITY_ID);
    verify(extractionGatewayService, Mockito.times(1)).deleteActivityExtraction(SURVEY_ID, ACTIVITY_ID);
  }

  @Test(expected = HttpResponseException.class)
  public void deleteActivityExtraction_method_should_handle_IOException() throws IOException {
    when(surveyActivity.isDiscarded()).thenReturn(false);
    when(surveyActivity.couldBeExtracted()).thenReturn(true);
    doThrow(new MalformedURLException()).when(extractionGatewayService).deleteActivityExtraction(SURVEY_ID, ACTIVITY_ID);
    activityExtractionFacade.deleteActivityExtraction(ACTIVITY_ID);
  }

  @Test
  public void synchronizeSurveyActivityExtractions_method_should_create_extraction_for_each_activity_without_extraction() throws IOException {
    when(extractionGatewayService.getSurveyActivityIdsWithExtraction(SURVEY_ID)).thenReturn(gatewayResponse);
    when(gatewayResponse.getData()).thenReturn("[" + ACTIVITY_ID + "]");
    List<String> activitiesIdsWithExtraction = new ArrayList<>();
    activitiesIdsWithExtraction.add(ACTIVITY_ID);
    List<ObjectId> activityOidsWithoutExtraction = new ArrayList<>();
    activityOidsWithoutExtraction.add(new ObjectId(ACTIVITY_ID_WITHOUT_EXTRACTION));
    when(activityFacade.getActivityIds(ACRONYM, VERSION, activitiesIdsWithExtraction)).thenReturn(activityOidsWithoutExtraction);
    when(activityFacade.getByID(ACTIVITY_ID_WITHOUT_EXTRACTION)).thenReturn(surveyActivity);
    when(surveyActivity.isDiscarded()).thenReturn(false);
    when(surveyActivity.couldBeExtracted()).thenReturn(true);
    activityExtractionFacade.synchronizeSurveyActivityExtractions(ACRONYM, VERSION);
    verify(extractionGatewayService, Mockito.times(1)).getSurveyActivityIdsWithExtraction(SURVEY_ID);
  }

  @Test(expected = HttpResponseException.class)
  public void synchronizeSurveyActivityExtractions_method_should_handle_IOException() throws IOException {
    doThrow(new MalformedURLException()).when(extractionGatewayService).getSurveyActivityIdsWithExtraction(SURVEY_ID);
    activityExtractionFacade.synchronizeSurveyActivityExtractions(ACRONYM, VERSION);
  }

  @Test
  public void forceSynchronizeSurveyActivityExtractions_method_should_create_extraction_for_each_activity_without_extraction() throws IOException {
    when(extractionGatewayService.getSurveyActivityIdsWithExtraction(SURVEY_ID)).thenReturn(gatewayResponse);
    when(gatewayResponse.getData()).thenReturn("[" + ACTIVITY_ID + "]");
    List<ObjectId> activityOidsWithoutExtraction = new ArrayList<>();
    activityOidsWithoutExtraction.add(new ObjectId(ACTIVITY_ID_WITHOUT_EXTRACTION));
    when(activityFacade.getActivityIds(ACRONYM, VERSION, null)).thenReturn(activityOidsWithoutExtraction);
    when(activityFacade.getByID(ACTIVITY_ID_WITHOUT_EXTRACTION)).thenReturn(surveyActivity);
    when(surveyActivity.isDiscarded()).thenReturn(false);
    when(surveyActivity.couldBeExtracted()).thenReturn(true);
    activityExtractionFacade.forceSynchronizeSurveyActivityExtractions(ACRONYM, VERSION);
    verify(extractionGatewayService, Mockito.times(1)).createOrUpdateActivityExtraction(activityExtraction.toJson());
  }

  @Test(expected = HttpResponseException.class)
  public void forceSynchronizeSurveyActivityExtractions_method_should_handle_IOException() throws IOException {
    List<ObjectId> activityOidsWithoutExtraction = new ArrayList<>();
    activityOidsWithoutExtraction.add(new ObjectId(ACTIVITY_ID_WITHOUT_EXTRACTION));
    when(activityFacade.getActivityIds(ACRONYM, VERSION, null)).thenReturn(activityOidsWithoutExtraction);
    when(activityFacade.getByID(ACTIVITY_ID_WITHOUT_EXTRACTION)).thenReturn(surveyActivity);
    doThrow(new IOException()).when(extractionGatewayService).createOrUpdateActivityExtraction(Mockito.anyString());
    activityExtractionFacade.forceSynchronizeSurveyActivityExtractions(ACRONYM, VERSION);
  }


  @Test
  public void forceCreateOrUpdateActivityExtraction_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
    doReturn(true).when(surveyActivity).isFinalized();
    when(surveyActivity.couldBeExtracted()).thenReturn(true);
    doNothing().when(extractionGatewayService).createOrUpdateActivityExtraction(ACTIVITY_ID);
    activityExtractionFacade.forceCreateOrUpdateActivityExtraction(ACTIVITY_ID);
    verify(extractionGatewayService, Mockito.times(1)).createOrUpdateActivityExtraction(activityExtraction.toJson());
  }

  @Test(expected = HttpResponseException.class)
  public void forceCreateOrUpdateActivityExtraction_method_should_handle_ValidationException_in_case_discarded_activity() throws IOException {
    when(surveyActivity.isDiscarded()).thenReturn(true);
    doThrow(new IOException()).when(extractionGatewayService).createOrUpdateActivityExtraction(Mockito.anyString());
    activityExtractionFacade.forceCreateOrUpdateActivityExtraction(ACTIVITY_ID);
  }

  @Test
  public void getSurveyActivitiesExtractionAsCsv_method_should_return_bytes_array() throws IOException, DataNotFoundException {
    when(extractionGatewayService.getCsvSurveyExtraction(SURVEY_ID)).thenReturn(gatewayResponse);
    when(extractionService.createExtraction(csvExtraction)).thenReturn(CSV_BYTES);
    assertEquals(CSV_BYTES, activityExtractionFacade.getSurveyActivitiesExtractionAsCsv(ACRONYM, VERSION));
  }

  @Test(expected = HttpResponseException.class)
  public void getSurveyActivitiesExtractionAsCsv_method_should_handle_MalformedURLException() throws IOException {
    when(extractionGatewayService.getCsvSurveyExtraction(SURVEY_ID)).thenThrow(new MalformedURLException());
    activityExtractionFacade.getSurveyActivitiesExtractionAsCsv(ACRONYM, VERSION);
  }

  @Test(expected = HttpResponseException.class)
  public void getSurveyActivitiesExtractionAsCsv_method_should_handle_DataNotFoundException() throws IOException, DataNotFoundException {
    when(extractionGatewayService.getCsvSurveyExtraction(SURVEY_ID)).thenReturn(gatewayResponse);
    when(extractionService.createExtraction(csvExtraction)).thenThrow(new DataNotFoundException());
    activityExtractionFacade.getSurveyActivitiesExtractionAsCsv(ACRONYM, VERSION);
  }

  @Test
  public void getSurveyActivitiesExtractionAsJson_method_should_return_bytes_array() throws IOException {
    when(extractionGatewayService.getJsonSurveyExtraction(SURVEY_ID)).thenReturn(gatewayResponse);
    when(gatewayResponse.getData()).thenReturn(EXTRACTIONS_JSON);
    assertEquals(EXTRACTIONS_JSON_SIZE, activityExtractionFacade.getSurveyActivitiesExtractionAsJson(ACRONYM, VERSION).size());
  }

  @Test(expected = HttpResponseException.class)
  public void getSurveyActivitiesExtractionAsJson_method_should_handle_MalformedURLException() throws IOException {
    when(extractionGatewayService.getJsonSurveyExtraction(SURVEY_ID)).thenThrow(new MalformedURLException());
    activityExtractionFacade.getSurveyActivitiesExtractionAsJson(ACRONYM, VERSION);
  }


  @Test
  public void getRscriptSurveyExtractionAsCsv_method_should_return_bytes_array() throws IOException {
    when(extractionGatewayService.getRscriptSurveyExtraction(surveyExtraction.toJson())).thenReturn(gatewayResponse);
    assertEquals(BYTES, activityExtractionFacade.getRscriptSurveyExtractionAsCsv(SURVEY_EXTRACTION_JSON));
  }

  @Test(expected = HttpResponseException.class)
  public void getRscriptSurveyExtractionAsCsv_method_should_handle_IOException() throws IOException {
    when(extractionGatewayService.getRscriptSurveyExtraction(surveyExtraction.toJson())).thenThrow(new IOException());
    activityExtractionFacade.getRscriptSurveyExtractionAsCsv(SURVEY_EXTRACTION_JSON);
  }

  @Test(expected = HttpResponseException.class)
  public void getRscriptSurveyExtractionAsCsv_method_should_handle_DataNotFoundException() throws IOException, DataNotFoundException {
    when(extractionGatewayService.getRscriptSurveyExtraction(surveyExtraction.toJson())).thenReturn(gatewayResponse);
    doReturn(CSV_JSON).when(gatewayResponse).getData();
    doThrow(new DataNotFoundException("")).when(extractionService).createExtraction(csvExtraction);
    activityExtractionFacade.getRscriptSurveyExtractionAsCsv(SURVEY_EXTRACTION_JSON);
  }

  @Test
  public void getRscriptSurveyExtractionAsJson_method_should_return_bytes_array() throws IOException {
    when(extractionGatewayService.getRscriptSurveyExtraction(surveyExtraction.toJson())).thenReturn(gatewayResponse);
    when(gatewayResponse.getData()).thenReturn(R_SCRIPT_JSON_RESULT);
    assertEquals(R_SCRIPT_JSON_RESULT, activityExtractionFacade.getRscriptSurveyExtractionAsJson(SURVEY_EXTRACTION_JSON));
  }

  @Test(expected = HttpResponseException.class)
  public void getRscriptSurveyExtractionAsJson_method_should_handle_IOException() throws IOException {
    when(extractionGatewayService.getRscriptSurveyExtraction(surveyExtraction.toJson())).thenThrow(new IOException());
    activityExtractionFacade.getRscriptSurveyExtractionAsJson(SURVEY_EXTRACTION_JSON);
  }

}
