package br.org.otus.laboratory.participant.api;

import br.org.otus.laboratory.participant.ParticipantLaboratoryService;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

  @Test
  public void getLaboratoryExtractionByParticipant_should_call_getLaboratoryExtraction_method() throws DataNotFoundException {
    facade.getLaboratoryExtraction();
    verify(participantLaboratoryService, times(1)).getLaboratoryExtraction();
  }

  @Test
  public void convertAlicotRole_method_should_evoke_serviceMethod() {
    facade.convertAliquotRole(convertedAliquot);
    verify(participantLaboratoryService, times(1)).convertAliquotRole(convertedAliquot);
  }
}
