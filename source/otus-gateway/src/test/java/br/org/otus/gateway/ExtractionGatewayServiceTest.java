package br.org.otus.gateway;

import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.request.JsonDELETEUtility;
import br.org.otus.gateway.request.JsonGETUtility;
import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.request.JsonPUTRequestUtility;
import br.org.otus.gateway.resource.ExtractionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ExtractionGatewayService.class})
public class ExtractionGatewayServiceTest {

  private static final String HOST = "http://localhost:";
  private static final String PORT = "53004";
  private static final String SURVEY_ID = "5e0658135b4ff40f8916d2b5";
  private static final String ACTIVITY_ID = "5e0658135b4ff40f8916d2b6";
  private static final String ACTIVITY_EXTRACTION_JSON = "{}";
  private static final String SURVEY_EXTRACTION_JSON = "{}";
  private static final GatewayResponse EXPECTED_GATEWAY_RESPONSE = null;
  private static final String R_SCRIPT_JSON = "{}";
  private static final String R_SCRIPT_NAME = "script";

  @InjectMocks
  private ExtractionGatewayService extractionGatewayService;

  @Mock
  private ExtractionMicroServiceResources extractionMicroServiceResources;
  @Mock
  private URL requestURL;
  @Mock
  private JsonGETUtility jsonGETUtility;
  @Mock
  private JsonPUTRequestUtility jsonPUTUtility;
  @Mock
  private JsonPOSTUtility jsonPOSTUtility;
  @Mock
  private JsonDELETEUtility jsonDELETEUtility;
  @Mock
  private GatewayResponse gatewayResponse;


  @Before
  public void setUp() throws Exception {
    Whitebox.setInternalState(extractionMicroServiceResources, "HOST", HOST);
    Whitebox.setInternalState(extractionMicroServiceResources, "PORT", PORT);
    PowerMockito.whenNew(ExtractionMicroServiceResources.class).withAnyArguments()
      .thenReturn(extractionMicroServiceResources);

    PowerMockito.whenNew(JsonGETUtility.class).withAnyArguments().thenReturn(jsonGETUtility);
    PowerMockito.whenNew(JsonPUTRequestUtility.class).withAnyArguments().thenReturn(jsonPUTUtility);
    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.whenNew(JsonDELETEUtility.class).withAnyArguments().thenReturn(jsonDELETEUtility);
    PowerMockito.whenNew(GatewayResponse.class).withNoArguments().thenReturn(gatewayResponse);
  }

  @Test
  public void createActivityExtraction_method_should_send_POST_request() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionCreateAddress()).thenReturn(requestURL);
    extractionGatewayService.createOrUpdateActivityExtraction(ACTIVITY_EXTRACTION_JSON);
    verify(jsonPUTUtility, Mockito.times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void createActivityExtraction_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionCreateAddress()).thenReturn(requestURL);
    when(jsonPUTUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.createOrUpdateActivityExtraction(ACTIVITY_EXTRACTION_JSON);
  }

  @Test
  public void deleteActivityExtraction_method_should_send_POST_request() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionDeleteAddress(SURVEY_ID, ACTIVITY_ID)).thenReturn(requestURL);
    extractionGatewayService.deleteActivityExtraction(SURVEY_ID, ACTIVITY_ID);
    verify(jsonDELETEUtility, Mockito.times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void deleteActivityExtraction_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionDeleteAddress(SURVEY_ID, ACTIVITY_ID)).thenReturn(requestURL);
    when(jsonDELETEUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.deleteActivityExtraction(SURVEY_ID, ACTIVITY_ID);
  }

  @Test
  public void getSurveyActivityIdsWithExtraction_method_should_send_POST_request() throws IOException {
    when(extractionMicroServiceResources.getSurveyActivityIdsWithExtractionAddress(SURVEY_ID)).thenReturn(requestURL);
    extractionGatewayService.getSurveyActivityIdsWithExtraction(SURVEY_ID);
    verify(jsonGETUtility, Mockito.times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void getSurveyActivityIdsWithExtraction_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getSurveyActivityIdsWithExtractionAddress(SURVEY_ID)).thenReturn(requestURL);
    when(jsonGETUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.getSurveyActivityIdsWithExtraction(SURVEY_ID);
  }

  @Test
  public void getCsvSurveyExtraction_method_should_return_GatewayResponse() throws IOException {
    when(extractionMicroServiceResources.getCsvSurveyExtractionAddress(SURVEY_ID)).thenReturn(requestURL);
    assertEquals(EXPECTED_GATEWAY_RESPONSE, extractionGatewayService.getCsvSurveyExtraction(SURVEY_ID));
  }

  @Test(expected = ReadRequestException.class)
  public void getCsvSurveyExtraction_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getCsvSurveyExtractionAddress(SURVEY_ID)).thenReturn(requestURL);
    when(jsonGETUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.getCsvSurveyExtraction(SURVEY_ID);
  }

  @Test
  public void getJsonSurveyExtractionAddress_method_should_return_GatewayResponse() throws IOException {
    when(extractionMicroServiceResources.getJsonSurveyExtractionAddress(SURVEY_ID)).thenReturn(requestURL);
    assertEquals(EXPECTED_GATEWAY_RESPONSE, extractionGatewayService.getJsonSurveyExtraction(SURVEY_ID));
  }

  @Test(expected = ReadRequestException.class)
  public void getJsonSurveyExtractionAddress_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getJsonSurveyExtractionAddress(SURVEY_ID)).thenReturn(requestURL);
    when(jsonGETUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.getJsonSurveyExtraction(SURVEY_ID);
  }

  @Test
  public void createOrUpdateRscript_method_should_send_PUT_request() throws IOException {
    when(extractionMicroServiceResources.getRScriptCreationAddress()).thenReturn(requestURL);
    extractionGatewayService.createOrUpdateRscript(R_SCRIPT_JSON);
    verify(jsonPUTUtility, Mockito.times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void createOrUpdateRscript_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getRScriptCreationAddress()).thenReturn(requestURL);
    when(jsonPUTUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.createOrUpdateRscript(R_SCRIPT_JSON);
  }

  @Test
  public void getRscript_method_should_return_GatewayResponse() throws IOException, URISyntaxException {
    when(extractionMicroServiceResources.getRScriptGetterAddress(R_SCRIPT_NAME)).thenReturn(requestURL);
    assertEquals(EXPECTED_GATEWAY_RESPONSE, extractionGatewayService.getRscript(R_SCRIPT_NAME));
  }

  @Test(expected = ReadRequestException.class)
  public void getRscript_method_should_throw_ReadRequestException() throws IOException, URISyntaxException {
    when(extractionMicroServiceResources.getRScriptGetterAddress(R_SCRIPT_NAME)).thenReturn(requestURL);
    when(jsonGETUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.getRscript(R_SCRIPT_NAME);
  }

  @Test
  public void deleteRscript_method_should_send_DELETE_request() throws IOException, URISyntaxException {
    when(extractionMicroServiceResources.getRScriptDeleteAddress(R_SCRIPT_NAME)).thenReturn(requestURL);
    extractionGatewayService.deleteRscript(R_SCRIPT_NAME);
    verify(jsonDELETEUtility, Mockito.times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void deleteRscript_method_should_throw_ReadRequestException() throws IOException, URISyntaxException {
    when(extractionMicroServiceResources.getRScriptDeleteAddress(R_SCRIPT_NAME)).thenReturn(requestURL);
    when(jsonDELETEUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.deleteRscript(R_SCRIPT_NAME);
  }


  @Test
  public void getRscriptSurveyExtraction_method_should_return_GatewayResponse() throws IOException {
    when(extractionMicroServiceResources.getRScriptJsonSurveyExtractionAddress()).thenReturn(requestURL);
    assertEquals(EXPECTED_GATEWAY_RESPONSE, extractionGatewayService.getRscriptSurveyExtraction(SURVEY_EXTRACTION_JSON));
  }

  @Test(expected = ReadRequestException.class)
  public void getRscriptSurveyExtraction_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getRScriptJsonSurveyExtractionAddress()).thenReturn(requestURL);
    when(jsonPOSTUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.getRscriptSurveyExtraction(SURVEY_EXTRACTION_JSON);
  }

}
