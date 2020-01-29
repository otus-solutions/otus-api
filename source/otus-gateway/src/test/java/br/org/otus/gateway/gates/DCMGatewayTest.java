package br.org.otus.gateway.gates;

import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.org.otus.gateway.request.JsonPOSTUtility;
import br.org.otus.gateway.resource.DCMMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DCMGatewayService.class})
public class DCMGatewayTest {
  private static String BODY = "\"{\"recruitmentNumber\":123456,\"examName\":\"DCMRetinography\",\"sending\":0,\"eyeSelected\":\"left\"}\"";
  private static String RESPONSE_TO_RETINOGRAPHY = "\"{\"data\":{\"date\":2019-09-09T17:40:34.699Z,\"eye\":\"left,\"result\":\"R0lGODlhPQBEAPeoAJos595kzAP==\"}}\"";
  private static String RESPONSE_TO_ULTRASOUND = "\"{\"data\":{\"date\":2019-09-09T17:40:34.699Z,\"result\":\"R0lGODlhPQBEAPeoAJos595kzAP==\"}}\"";
  private static String HOST = "http://localhost:";
  private static String PORT = "8081";

  @InjectMocks
  private DCMGatewayService gateway;
  @Mock
  private URL requestURL;
  @Mock
  private JsonPOSTUtility jsonPOSTUtility;
  @Mock
  private GatewayResponse gatewayResponse;
  private DCMMicroServiceResources resources = PowerMockito.spy(new DCMMicroServiceResources());

  @Test
  public void findRetinography_method_should_call_expected_methods() throws Exception {
    PowerMockito.whenNew(DCMMicroServiceResources.class).withNoArguments().thenReturn(resources);
    Whitebox.setInternalState(resources, "HOST", HOST);
    Whitebox.setInternalState(resources, "PORT", PORT);

    PowerMockito.when(resources.getRetinographyImageAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenReturn(RESPONSE_TO_RETINOGRAPHY);

    this.gateway.findRetinography(BODY);

    Mockito.verify(resources, Mockito.times(1)).getRetinographyImageAddress();
    Mockito.verify(jsonPOSTUtility, Mockito.times(1)).finish();
  }

  @Test
  public void findUltrasound_method_should_call_expected_methods() throws Exception {
    PowerMockito.whenNew(DCMMicroServiceResources.class).withNoArguments().thenReturn(resources);
    Whitebox.setInternalState(resources, "HOST", HOST);
    Whitebox.setInternalState(resources, "PORT", PORT);

    PowerMockito.when(resources.getUltrasoundImageAddress()).thenReturn(requestURL);

    PowerMockito.whenNew(JsonPOSTUtility.class).withAnyArguments().thenReturn(jsonPOSTUtility);
    PowerMockito.when(jsonPOSTUtility.finish()).thenReturn(RESPONSE_TO_ULTRASOUND);

    this.gateway.findUltrasound(BODY);

    Mockito.verify(resources, Mockito.times(1)).getUltrasoundImageAddress();
    Mockito.verify(jsonPOSTUtility, Mockito.times(1)).finish();
  }

}
