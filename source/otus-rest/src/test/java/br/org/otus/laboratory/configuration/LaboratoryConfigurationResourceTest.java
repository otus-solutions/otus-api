package br.org.otus.laboratory.configuration;

import br.org.otus.ResourceTestsParent;
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
  @Mock
  private LaboratoryConfigurationService laboratoryConfigurationService;


  @Test
  public void updateTubeCustomMetadata_method_should_return_empty_response(){
    List<TubeCustomMetadata> tubeCustomMetadata = new ArrayList<>();
    when(laboratoryConfigurationFacade.getTubeMedataData(TUBE_JSON)).thenReturn(tubeCustomMetadata);

    String response = resource.getTubeMedataData(TUBE_JSON);
    verify(laboratoryConfigurationFacade, Mockito.times(1)).getTubeMedataData(TUBE_JSON);
    assertEquals(encapsulateExpectedResponse(tubeCustomMetadata.toString()), response);
  }
}
