package br.org.otus.laboratory.participant.api;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.participant.ParticipantLaboratoryService;

@RunWith(PowerMockRunner.class)
public class ParticipantLaboratoryFacadeTest {

  @InjectMocks
  private ParticipantLaboratoryFacade facade;

  @Mock
  private ParticipantLaboratoryService participantLaboratoryService;

  @Test
  public void getLaboratoryExtractionByParticipant_should_call_getLaboratoryExtraction_method() throws DataNotFoundException {
    facade.getLaboratoryExtraction();
    
    Mockito.verify(participantLaboratoryService, Mockito.times(1)).getLaboratoryExtraction();
  }

}
