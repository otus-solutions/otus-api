package br.org.otus.laboratory.configuration;

import br.org.otus.ResourceTestsParent;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquotConfiguration;
import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class LaboratoryConfigurationResourceTest extends ResourceTestsParent {

  private static final String TUBE_JSON = "{}";

  @InjectMocks
  private LaboratoryConfigurationResource resource;
  @Mock
  private LaboratoryConfigurationFacade laboratoryConfigurationFacade;

  @Test
  public void getCheckingExist_method_should_return_true_response(){
    when(laboratoryConfigurationFacade.getCheckingExist()).thenReturn(true);
    assertEquals(encapsulateExpectedResponse("true"), resource.getCheckingExist());
  }

  @Test
  public void getCheckingExist_method_should_return_false_response(){
    when(laboratoryConfigurationFacade.getCheckingExist()).thenReturn(false);
    assertEquals(encapsulateExpectedResponse("false"), resource.getCheckingExist());
  }

  @Test
  public void getDescriptor_method_should_return_LaboratoryConfigurationDTO_instance_as_json(){
    LaboratoryConfigurationDTO dto = new LaboratoryConfigurationDTO(new LaboratoryConfiguration());
    when(laboratoryConfigurationFacade.getLaboratoryConfiguration()).thenReturn(dto);
    final String expectedResponse = "{\"codeConfiguration\":{},\"tubeConfiguration\":{\"tubeDescriptors\":[]},\"aliquotConfiguration\":{\"aliquotCenterDescriptors\":[]},\"collectMomentConfiguration\":{\"momentDescriptors\":[]},\"collectGroupConfiguration\":{\"groupDescriptors\":[]},\"labelPrintConfiguration\":{\"orders\":{}}}";
    assertEquals(encapsulateExpectedResponse(expectedResponse), resource.getDescriptor());
  }

  @Test
  public void getAliquotConfiguration_method_should_return_LaboratoryConfigurationDTO_instance_as_json(){
    AliquotConfiguration aliquotConfiguration = new AliquotConfiguration(new ArrayList<>());
    when(laboratoryConfigurationFacade.getAliquotConfiguration()).thenReturn(aliquotConfiguration);
    final String expectedResponse = "{\"aliquotCenterDescriptors\":[]}";
    assertEquals(encapsulateExpectedResponse(expectedResponse), resource.getAliquotConfiguration());
  }

  @Test
  public void getAliquotDescriptors_method_should_return_LaboratoryConfigurationDTO_instance_as_json(){
    when(laboratoryConfigurationFacade.getAliquotDescriptors()).thenReturn(new ArrayList<>());
    final String expectedResponse = "[]";
    assertEquals(encapsulateExpectedResponse(expectedResponse), resource.getAliquotDescriptors());
  }

  @Test
  public void updateTubeCustomMetadata_method_should_return_empty_response(){
    List<TubeCustomMetadata> tubeCustomMetadata = new ArrayList<>();
    when(laboratoryConfigurationFacade.getTubeMedataData(TUBE_JSON)).thenReturn(tubeCustomMetadata);

    String response = resource.getTubeMedataData(TUBE_JSON);
    verify(laboratoryConfigurationFacade, Mockito.times(1)).getTubeMedataData(TUBE_JSON);
    assertEquals(encapsulateExpectedResponse(tubeCustomMetadata.toString()), response);
  }
}
