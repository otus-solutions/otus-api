package br.org.otus.extraction.rest;

import br.org.otus.AuthenticationResourceTestsParent;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.user.dto.ManagementUserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import br.org.otus.extraction.ExtractionFacade;
import br.org.otus.user.api.UserFacade;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AuthorizationHeaderReader.class, javax.ws.rs.core.Response.class})
public class ExtractionResourceTest extends AuthenticationResourceTestsParent {

  private static final String ACRONYM = "ANTC";
  private static final Integer VERSION = 1;
  private static final String CENTER = "RS";
  private static final String EXTRACTION_TOKEN = "123";
  private static final String PIPELINE_NAME = "pipeline";
  private static final byte[] BYTES = new byte[1];

  @InjectMocks
  private ExtractionResource extractionResource;

  @Mock
  private UserFacade userFacade;
  @Mock
  private ExtractionFacade extractionFacade;

  @Mock
  private ManagementUserDto managementUserDto;


  @Before
  public void setUp() {
    PowerMockito.when(extractionFacade.createActivityExtraction(ACRONYM, VERSION)).thenReturn(null);
  }

  @Test
  public void extractActivities_method_should_verify_method_createActivityExtraction_have_been_called() {
    extractionResource.extractActivities(ACRONYM, VERSION);
    Mockito.verify(extractionFacade).createActivityExtraction(ACRONYM, VERSION);
  }

  @Test
  public void listSurveyVersions_method_should_verify_method_listSurveyVersions_have_been_called() {
    extractionResource.listSurveyVersions(ACRONYM);
    Mockito.verify(extractionFacade).listSurveyVersions(ACRONYM);
  }

  @Test
  public void extractExamsValues_method_should_verify_method_extractExamsValues_have_been_called() {
    extractionResource.extractExamsValues();
    Mockito.verify(extractionFacade).createLaboratoryExamsValuesExtraction();
  }

  @Test
  public void extractLaboratory_method_should_call_createLaboratoryExtraction_method() {
    extractionResource.extractLaboratory();
    Mockito.verify(extractionFacade).createLaboratoryExtraction();
  }

  @Test
  public void extractAnnexesReport_method_should_verify_method_extractAnnexesReport_have_been_called() {
    extractionResource.extractAnnexesReport(ACRONYM, VERSION);
    Mockito.verify(extractionFacade).createAttachmentsReportExtraction(ACRONYM, VERSION);
  }

  @Test
  public void extractActivitiesProgress_method_should_call_createActivityProgressExtraction_method() {
    extractionResource.extractActivitiesProgress(CENTER);
    Mockito.verify(extractionFacade).createActivityProgressExtraction(CENTER);
  }

  @Test
  public void enableUsers_method_should_call_userFacade_enableExtraction_method() {
    String response = extractionResource.enableUsers(managementUserDto);
    Mockito.verify(userFacade).enableExtraction(managementUserDto);
    assertEquals(EMPTY_RESPONSE, response);
  }

  @Test
  public void disableUsers_method_should_call_userFacade_disableExtraction_method() {
    String response = extractionResource.disableUsers(managementUserDto);
    Mockito.verify(userFacade).disableExtraction(managementUserDto);
    assertEquals(EMPTY_RESPONSE, response);
  }

  @Test
  public void enableIps_method_should_call_userFacade_updateExtractionIps_method() {
    String response = extractionResource.enableIps(managementUserDto);
    Mockito.verify(userFacade).updateExtractionIps(managementUserDto);
    assertEquals(EMPTY_RESPONSE, response);
  }

  @Test
  public void fetch_method_should_call_userFacade_updateExtractionIps_method() {
    final ArrayList<String> OIDs = new ArrayList<>();
    when(extractionFacade.downloadFiles(OIDs)).thenReturn(BYTES);
    Response response = extractionResource.fetch(OIDs);
    Mockito.verify(extractionFacade).downloadFiles(OIDs);
    assertEquals(SUCCESS_STATUS, response.getStatus());
  }

  @Test
  public void getToken_method_should_call_userFacade_getExtractionToken_method() {
    mockContextToSetUserEmail();
    PowerMockito.when(userFacade.getExtractionToken(USER_EMAIL)).thenReturn(EXTRACTION_TOKEN);
    String response = extractionResource.getToken(request);
    Mockito.verify(userFacade).getExtractionToken(USER_EMAIL);
    assertEquals(encapsulateExpectedStringResponse(EXTRACTION_TOKEN), response);
  }

  @Test
  public void extractFromPipeline_method_should_call_createExtractionFromPipeline_method() {
    extractionResource.extractFromPipeline(PIPELINE_NAME);
    Mockito.verify(extractionFacade).createExtractionFromPipeline(PIPELINE_NAME);
  }

}
