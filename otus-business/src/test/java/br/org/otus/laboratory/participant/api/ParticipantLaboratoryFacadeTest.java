package br.org.otus.laboratory.participant.api;

import br.org.otus.laboratory.participant.ParticipantLaboratoryService;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class ParticipantLaboratoryFacadeTest {

  @InjectMocks
  private ParticipantLaboratoryFacade facade;
  @Mock
  private ParticipantLaboratoryService participantLaboratoryService;
  @Mock
  private Aliquot convertedAliquot;
  @Spy
  DataNotFoundException dataNotFoundException = new DataNotFoundException();

  @Test
  public void getLaboratoryExtractionByParticipant_should_call_getLaboratoryExtraction_method() throws DataNotFoundException {
    facade.getLaboratoryExtraction();
    verify(participantLaboratoryService, times(1)).getLaboratoryExtraction();
  }

  @Test
  public void convertAlicotRole_method_should_evoke_serviceMethod() throws DataNotFoundException {
    facade.convertAliquotRole(convertedAliquot);
    verify(participantLaboratoryService, times(1)).convertAliquotRole(convertedAliquot);
  }

  @Test(expected = HttpResponseException.class)
  public void convertAlicotRole_method_should_throws_HttpResponseException() throws DataNotFoundException {
    PowerMockito.when(participantLaboratoryService.convertAliquotRole(convertedAliquot)).thenThrow(dataNotFoundException);
    facade.convertAliquotRole(convertedAliquot);
  }
}
