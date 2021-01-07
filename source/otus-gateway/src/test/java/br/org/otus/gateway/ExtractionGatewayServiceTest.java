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
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ExtractionGatewayService.class})
public class ExtractionGatewayServiceTest {

  private static final String HOST = "http://localhost:";
  private static final String PORT = "53004";
  private static final String PIPELINE_NAME = "pipelineName";
  private static final String ACTIVITY_ID = "5e0658135b4ff40f8916d2b5";
  private static final GatewayResponse EXPECTED_GATEWAY_RESPONSE = null;

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
  public void getPipelineExtraction_method_should_return_GatewayResponse() throws IOException {
    when(extractionMicroServiceResources.getPipelineJsonExtractionAddress(PIPELINE_NAME)).thenReturn(requestURL);
    assertEquals(EXPECTED_GATEWAY_RESPONSE, extractionGatewayService.getPipelineJsonExtraction(PIPELINE_NAME));
  }

  @Test(expected = ReadRequestException.class)
  public void getPipelineExtraction_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getPipelineJsonExtractionAddress(PIPELINE_NAME)).thenReturn(requestURL);
    when(jsonGETUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.getPipelineJsonExtraction(PIPELINE_NAME);
  }


  @Test
  public void createActivityExtraction_method_should_send_POST_request() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionCreateAddress(ACTIVITY_ID)).thenReturn(requestURL);
    extractionGatewayService.createActivityExtraction(ACTIVITY_ID);
    verify(jsonPOSTUtility, Mockito.times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void createActivityExtraction_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionCreateAddress(ACTIVITY_ID)).thenReturn(requestURL);
    when(jsonPOSTUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.createActivityExtraction(ACTIVITY_ID);
  }


  @Test
  public void updateActivityExtraction_method_should_send_POST_request() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionUpdateAddress(ACTIVITY_ID)).thenReturn(requestURL);
    extractionGatewayService.updateActivityExtraction(ACTIVITY_ID);
    verify(jsonPUTUtility, Mockito.times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void updateActivityExtraction_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionUpdateAddress(ACTIVITY_ID)).thenReturn(requestURL);
    when(jsonPUTUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.updateActivityExtraction(ACTIVITY_ID);
  }


  @Test
  public void deleteActivityExtraction_method_should_send_POST_request() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionDeleteAddress(ACTIVITY_ID)).thenReturn(requestURL);
    extractionGatewayService.deleteActivityExtraction(ACTIVITY_ID);
    verify(jsonDELETEUtility, Mockito.times(1)).finish();
  }

  @Test(expected = ReadRequestException.class)
  public void deleteActivityExtraction_method_should_throw_ReadRequestException() throws IOException {
    when(extractionMicroServiceResources.getActivityExtractionDeleteAddress(ACTIVITY_ID)).thenReturn(requestURL);
    when(jsonDELETEUtility.finish()).thenThrow(new IOException());
    extractionGatewayService.deleteActivityExtraction(ACTIVITY_ID);
  }

}
