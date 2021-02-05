package br.org.otus.extraction;

import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.response.exception.HttpResponseException;
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

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RscriptFacade.class})
public class RscriptFacadeTest {

  private static final String R_SCRIPT_JSON = "{}";
  private static final String R_SCRIPT_NAME = "script";

  @InjectMocks
  private RscriptFacade rscriptFacade;

  @Mock
  private ExtractionGatewayService extractionGatewayService;
  @Mock
  private GatewayResponse gatewayResponse;

  @Before
  public void setUp() throws Exception {
    PowerMockito.whenNew(ExtractionGatewayService.class).withNoArguments().thenReturn(extractionGatewayService);
  }

  @Test
  public void createOrUpdateRscript_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
    doNothing().when(extractionGatewayService).createOrUpdateRscript(R_SCRIPT_JSON);
    rscriptFacade.createOrUpdate(R_SCRIPT_JSON);
    verify(extractionGatewayService, Mockito.times(1)).createOrUpdateRscript(R_SCRIPT_JSON);
  }

  @Test(expected = HttpResponseException.class)
  public void createOrUpdateRscript_method_should_handle_IOException() throws IOException {
    doThrow(new IOException()).when(extractionGatewayService).createOrUpdateRscript(R_SCRIPT_JSON);
    rscriptFacade.createOrUpdate(R_SCRIPT_JSON);
  }

  @Test
  public void get_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
    doReturn(gatewayResponse).when(extractionGatewayService).getRscript(R_SCRIPT_NAME);
    when(gatewayResponse.getData()).thenReturn(R_SCRIPT_JSON);
    rscriptFacade.get(R_SCRIPT_NAME);
    verify(extractionGatewayService, Mockito.times(1)).getRscript(R_SCRIPT_NAME);
  }

  @Test(expected = HttpResponseException.class)
  public void get_method_should_handle_IOException() throws IOException {
    doThrow(new IOException()).when(extractionGatewayService).getRscript(R_SCRIPT_NAME);
    rscriptFacade.get(R_SCRIPT_NAME);
  }

  @Test
  public void delete_method_should_call_same_method_from_ExtractionGatewayService() throws IOException {
    doNothing().when(extractionGatewayService).deleteRscript(R_SCRIPT_NAME);
    rscriptFacade.delete(R_SCRIPT_NAME);
    verify(extractionGatewayService, Mockito.times(1)).deleteRscript(R_SCRIPT_NAME);
  }

  @Test(expected = HttpResponseException.class)
  public void delete_method_should_handle_IOException() throws IOException {
    doThrow(new IOException()).when(extractionGatewayService).deleteRscript(R_SCRIPT_NAME);
    rscriptFacade.delete(R_SCRIPT_NAME);
  }

}
