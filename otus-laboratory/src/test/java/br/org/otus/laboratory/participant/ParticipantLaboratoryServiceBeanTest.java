package br.org.otus.laboratory.participant;

import static org.junit.Assert.*;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.laboratory.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.util.JsonObjecParticipantLaboratoryFactory;
import br.org.otus.laboratory.participant.util.JsonObjectUpdateAliquotsDTOFactory;
import br.org.otus.laboratory.validators.ParticipantLaboratoryValidator;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantLaboratoryServiceBeanTest {

	private static final long RECRUIMENT_NUMBER = 12345;

	@InjectMocks
	private ParticipantLaboratoryServiceBean service;

	@Mock
	private ParticipantLaboratoryServiceBean laboratoryService;
	@Mock
	private ParticipantLaboratoryValidator aliquotUpdateValidator;

	private UpdateAliquotsDTO aliquotsDTO;
	private ParticipantLaboratory participantLaboratory;

	@Before
	public void setup() throws DataNotFoundException {
		JsonObjectUpdateAliquotsDTOFactory dtoFactory = new JsonObjectUpdateAliquotsDTOFactory();
		aliquotsDTO = UpdateAliquotsDTO.deserialize(dtoFactory.create().toString());
		
		JsonObjecParticipantLaboratoryFactory jsonObjecParticipantLaboratoryFactory = new JsonObjecParticipantLaboratoryFactory();
		participantLaboratory = ParticipantLaboratory.deserialize(jsonObjecParticipantLaboratoryFactory.create().toString());

		Mockito.when(laboratoryService.getLaboratory(RECRUIMENT_NUMBER)).thenReturn(participantLaboratory);
	}

	@Ignore
	@Test
	public void updateAliquots_method_should_call_methed_validate() throws DataNotFoundException, ValidationException {
		laboratoryService.updateAliquots(aliquotsDTO);

		Mockito.verify(aliquotUpdateValidator).validate();
	}

	@Ignore
	@Test
	public void UpdateAliquots_method_when_executed_with_success_should_call_method_update() throws DataNotFoundException, ValidationException {

		laboratoryService.updateAliquots(aliquotsDTO);

		Mockito.verify(laboratoryService).update(participantLaboratory);
	}

	@Test(expected = Exception.class)
	public void UpdateAliquots_method_should_throw_an_exception_when_aliquots_is_invalid() throws ValidationException, DataNotFoundException {

		Mockito.doThrow(new Exception()).when(aliquotUpdateValidator).validate();
		laboratoryService.updateAliquots(aliquotsDTO);

	}

	@Test
	public void when_the_method_is_executed_successfully_and_the_set_of_aliquots_are_valid_then_the_laboratory_must_be_updated() throws DataNotFoundException, ValidationException {
		
		ParticipantLaboratory laboratory = laboratoryService.getLaboratory(RECRUIMENT_NUMBER);
		assertEquals(laboratory.getTubes().get(0).getAliquots().size(), 0);
		
		laboratoryService.updateAliquots(aliquotsDTO);
	}

}
