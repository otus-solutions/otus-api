package br.org.otus.laboratory.configuration;

import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class LaboratoryConfigurationFacadeTest {

  private static final String TUBE_TYPE = "type";

  @InjectMocks
  private LaboratoryConfigurationFacade laboratoryConfigurationFacade;
  @Mock
  private LaboratoryConfigurationService laboratoryConfigurationService;

  @Before
  public void setUp(){

  }

  @Test
  public void getTubeMedataDataByType_method_should_invoke_service_method() throws DataNotFoundException {
    laboratoryConfigurationFacade.getTubeMedataData(TUBE_TYPE);
    verify(laboratoryConfigurationService, times(1)).getTubeCustomMedataData(TUBE_TYPE);
  }

  @Test(expected = HttpResponseException.class)
  public void getTubeMedataDataByType_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    when(laboratoryConfigurationService.getTubeCustomMedataData(TUBE_TYPE)).thenThrow(new DataNotFoundException());
    laboratoryConfigurationFacade.getTubeMedataData(TUBE_TYPE);
  }
}
