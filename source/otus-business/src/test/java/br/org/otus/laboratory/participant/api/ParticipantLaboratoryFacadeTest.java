package br.org.otus.laboratory.participant.api;

import br.org.otus.laboratory.participant.ParticipantLaboratoryService;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Tube.class})
public class ParticipantLaboratoryFacadeTest {

  private static final String TUBE_JSON = "{}";

  @InjectMocks
  private ParticipantLaboratoryFacade facade;
  @Mock
  private ParticipantLaboratoryService participantLaboratoryService;
  @Mock
  private Aliquot convertedAliquot;
  @Spy
  private DataNotFoundException dataNotFoundException = new DataNotFoundException();
  @Spy
  private ValidationException validationException = new ValidationException();

  private Tube tube = new Tube("", "", "", "");

  @Before
  public void setUp(){
    PowerMockito.mockStatic(Tube.class);
    PowerMockito.when(Tube.deserialize(TUBE_JSON)).thenReturn(tube);
  }

  @Test
  public void getLaboratoryExtractionByParticipant_should_call_getLaboratoryExtraction_method() throws DataNotFoundException {
    facade.getLaboratoryExtraction();
    verify(participantLaboratoryService, times(1)).getLaboratoryExtraction();
  }

  @Test
  public void convertAlicotRole_method_should_evoke_serviceMethod() throws DataNotFoundException, ValidationException {
    facade.convertAliquotRole(convertedAliquot);
    verify(participantLaboratoryService, times(1)).convertAliquotRole(convertedAliquot);
  }

  @Test(expected = HttpResponseException.class)
  public void convertAlicotRole_method_should_capture_DataNotFoundException() throws DataNotFoundException, ValidationException {
    PowerMockito.when(participantLaboratoryService.convertAliquotRole(convertedAliquot)).thenThrow(dataNotFoundException);
    facade.convertAliquotRole(convertedAliquot);
  }

  @Test(expected = HttpResponseException.class)
  public void convertAlicotRole_method_should_capture_ValidationException() throws DataNotFoundException, ValidationException {
    PowerMockito.when(participantLaboratoryService.convertAliquotRole(convertedAliquot)).thenThrow(validationException);
    facade.convertAliquotRole(convertedAliquot);
  }


  @Test
  public void updateTubeCustomMetadata_method_should_evoke_serviceMethod() throws DataNotFoundException {
    facade.updateTubeCustomMetadata(TUBE_JSON);
    verify(participantLaboratoryService, times(1)).updateTubeCustomMetadata(tube);
  }

  @Test(expected = HttpResponseException.class)
  public void updateTubeCustomMetadata_method_should_handle_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException(new Throwable(""))).when(participantLaboratoryService).updateTubeCustomMetadata(tube);
    facade.updateTubeCustomMetadata(TUBE_JSON);
  }
}
