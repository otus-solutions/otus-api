package br.org.otus.laboratory.configuration;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class LaboratoryConfigurationFacadeTest {

  private static final String TUBE_TYPE = "type";

  @InjectMocks
  private LaboratoryConfigurationFacade laboratoryConfigurationFacade;
  @Mock
  private LaboratoryConfigurationService laboratoryConfigurationService;


  @Test
  public void getCheckingExist_method_should_return_true() {
    when(laboratoryConfigurationService.getCheckingExist()).thenReturn(true);
    assertTrue(laboratoryConfigurationFacade.getCheckingExist());
  }

  @Test
  public void getCheckingExist_method_should_return_false() {
    when(laboratoryConfigurationService.getCheckingExist()).thenReturn(false);
    assertFalse(laboratoryConfigurationFacade.getCheckingExist());
  }


  @Test
  public void getLaboratoryConfiguration_method_should_invoke_service_method() throws DataNotFoundException {
    LaboratoryConfiguration laboratoryConfiguration = new LaboratoryConfiguration();
    when(laboratoryConfigurationService.getLaboratoryConfiguration()).thenReturn(laboratoryConfiguration);
    assertTrue(laboratoryConfigurationFacade.getLaboratoryConfiguration() instanceof LaboratoryConfigurationDTO);
  }

  @Test(expected = HttpResponseException.class)
  public void getLaboratoryConfiguration_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    when(laboratoryConfigurationService.getLaboratoryConfiguration()).thenThrow(new DataNotFoundException());
    laboratoryConfigurationFacade.getLaboratoryConfiguration();
  }


  @Test
  public void getAliquotConfiguration_method_should_invoke_service_method() throws DataNotFoundException {
    when(laboratoryConfigurationService.getAliquotConfiguration()).thenReturn(new AliquotConfiguration(null));
    assertTrue(laboratoryConfigurationFacade.getAliquotConfiguration() instanceof AliquotConfiguration);
  }

  @Test(expected = HttpResponseException.class)
  public void getAliquotConfiguration_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    when(laboratoryConfigurationService.getAliquotConfiguration()).thenThrow(new DataNotFoundException());
    laboratoryConfigurationFacade.getAliquotConfiguration();
  }


  @Test
  public void getAliquotDescriptors_method_should_invoke_service_method() throws DataNotFoundException {
    List<AliquoteDescriptor> aliquoteDescriptors = new ArrayList<>();
    when(laboratoryConfigurationService.getAliquotDescriptors()).thenReturn(aliquoteDescriptors);
    assertEquals(aliquoteDescriptors, laboratoryConfigurationFacade.getAliquotDescriptors());
  }

  @Test(expected = HttpResponseException.class)
  public void getAliquotDescriptors_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    when(laboratoryConfigurationService.getAliquotDescriptors()).thenThrow(new DataNotFoundException());
    laboratoryConfigurationFacade.getAliquotDescriptors();
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
