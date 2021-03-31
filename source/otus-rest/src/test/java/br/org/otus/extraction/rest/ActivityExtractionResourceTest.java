package br.org.otus.extraction.rest;

import br.org.otus.ResourceTestsParent;
import br.org.otus.extraction.ActivityExtractionFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class ActivityExtractionResourceTest extends ResourceTestsParent {

  private static final String ACRONYM = "ANTC";
  private static final Integer VERSION = 1;
  private static final String CENTER = "RS";
  private static final String ACTIVITY_ID = "5e0658135b4ff40f8916d2b6";
  private static final String SURVEY_EXTRACTION_JSON = "{}";
  private static final byte[] BYTES = new byte[1];

  @InjectMocks
  private ActivityExtractionResource activityExtractionResource;
  @Mock
  private ActivityExtractionFacade activityExtractionFacade;


  @Test
  public void listSurveyVersions_method_should_verify_method_listSurveyVersions_have_been_called() {
    activityExtractionResource.listSurveyVersions(ACRONYM);
    Mockito.verify(activityExtractionFacade).listSurveyVersions(ACRONYM);
  }

  @Test
  public void extractAnnexesReport_method_should_verify_method_extractAnnexesReport_have_been_called() {
    activityExtractionResource.extractAnnexesReport(ACRONYM, VERSION);
    Mockito.verify(activityExtractionFacade).createAttachmentsReportExtraction(ACRONYM, VERSION);
  }

  @Test
  public void extractActivitiesProgress_method_should_call_createActivityProgressExtraction_method() {
    activityExtractionResource.extractActivitiesProgress(CENTER);
    Mockito.verify(activityExtractionFacade).createActivityProgressExtraction(CENTER);
  }

  @Test
  public void fetch_method_should_call_userFacade_updateExtractionIps_method() {
    final ArrayList<String> OIDs = new ArrayList<>();
    when(activityExtractionFacade.downloadFiles(OIDs)).thenReturn(BYTES);
    Response response = activityExtractionResource.fetch(OIDs);
    Mockito.verify(activityExtractionFacade).downloadFiles(OIDs);
    assertEquals(SUCCESS_STATUS, response.getStatus());
  }


  @Test
  public void createOrUpdateActivityExtraction_method_should_call_facade_createOrUpdateActivityExtraction_method() {
    activityExtractionResource.createOrUpdateActivityExtraction(ACTIVITY_ID);
    Mockito.verify(activityExtractionFacade, Mockito.times(1)).createOrUpdateActivityExtraction(ACTIVITY_ID);
  }

  @Test
  public void deleteActivityExtraction_method_should_call_facade_deleteActivityExtraction_method() {
    activityExtractionResource.deleteActivityExtraction(ACTIVITY_ID);
    Mockito.verify(activityExtractionFacade, Mockito.times(1)).deleteActivityExtraction(ACTIVITY_ID);
  }

  @Test
  public void syncSurveyExtractions_method_should_call_facade_deleteActivityExtraction_method() {
    activityExtractionResource.syncSurveyExtractions(ACRONYM, VERSION);
    Mockito.verify(activityExtractionFacade, Mockito.times(1)).synchronizeSurveyActivityExtractions(ACRONYM, VERSION);
  }

  @Test
  public void forceSyncSurveyExtractions_method_should_call_facade_deleteActivityExtraction_method() {
    activityExtractionResource.forceSyncSurveyExtractions(ACRONYM, VERSION);
    Mockito.verify(activityExtractionFacade, Mockito.times(1)).forceSynchronizeSurveyActivityExtractions(ACRONYM, VERSION);
  }

  @Test
  public void getSurveyActivitiesExtractionAsCsv_method_should_call_facade_getSurveyActivitiesExtractionAsCsv_method() {
    activityExtractionResource.getSurveyActivitiesExtractionAsCsv(ACRONYM, VERSION);
    Mockito.verify(activityExtractionFacade, Mockito.times(1)).getSurveyActivitiesExtractionAsCsv(ACRONYM, VERSION);
  }

  @Test
  public void getSurveyActivitiesExtractionAsJson_method_should_call_facade_getSurveyActivitiesExtractionAsJson_method() {
    activityExtractionResource.getSurveyActivitiesExtractionAsJson(ACRONYM, VERSION);
    Mockito.verify(activityExtractionFacade, Mockito.times(1)).getSurveyActivitiesExtractionAsJson(ACRONYM, VERSION);
  }

  @Test
  public void getRscriptSurveyExtractionAsCsv_method_should_call_facade_getRscriptSurveyExtractionAsCsv_method() {
    activityExtractionResource.getRscriptSurveyExtractionAsCsv(SURVEY_EXTRACTION_JSON);
    Mockito.verify(activityExtractionFacade, Mockito.times(1)).getRscriptSurveyExtractionAsCsv(SURVEY_EXTRACTION_JSON);
  }

  @Test
  public void getRscriptSurveyExtractionAsJson_method_should_call_facade_getRscriptSurveyExtractionAsJson_method() {
    activityExtractionResource.getRscriptSurveyExtractionAsJson(SURVEY_EXTRACTION_JSON);
    Mockito.verify(activityExtractionFacade, Mockito.times(1)).getRscriptSurveyExtractionAsJson(SURVEY_EXTRACTION_JSON);
  }

}
