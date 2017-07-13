package br.org.otus.laboratory.participant;

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
import br.org.otus.laboratory.participant.util.JsonObjectFactory;
import br.org.otus.laboratory.participant.util.JsonObjectUpdateAliquotsDTOFactory;
import br.org.otus.laboratory.validators.ParticipantLaboratoryValidator;

@RunWith(MockitoJUnitRunner.class)
public class ParticipantLaboratoryServiceBeanTest {

	private static final long RECRUIMENT_NUMBER = 12345;

	@InjectMocks
	private ParticipantLaboratoryServiceBean service;

	@Mock
	private UpdateAliquotsDTO updateAliquotsDTO;
	@Mock
	private ParticipantLaboratoryValidator aliquotUpdateValidator;
	@Mock
	private ParticipantLaboratoryDao participantLaboratoryDao;

	private ParticipantLaboratoryServiceBean laboratoryService;
	private ParticipantLaboratory participantLaboratory;

	@Before
	public void setup() throws DataNotFoundException {
		JsonObjectFactory jsonObjecParticipantLaboratory = new JsonObjecParticipantLaboratoryFactory();
		participantLaboratory = ParticipantLaboratory.deserialize(jsonObjecParticipantLaboratory.create().toString());
		
		Mockito.when(service.getLaboratory(RECRUIMENT_NUMBER)).thenReturn(participantLaboratory);
	}

	@Ignore
	@Test
	public void updateAliquots_method_should_call_methed_validate() throws DataNotFoundException, ValidationException {
		
		laboratoryService.updateAliquots(updateAliquotsDTO);
		
		Mockito.verify(aliquotUpdateValidator).validate();
		
	}

	@Ignore
	@Test
	public void UpdateAliquots_method_when_executed_with_success_should_call_method_update() throws DataNotFoundException, ValidationException {
		
	}

	@Test(expected = Exception.class)
	public void UpdateAliquots_method_should_throw_an_exception_when_aliquots_is_invalid() throws ValidationException, DataNotFoundException {

		Mockito.doThrow(new Exception()).when(aliquotUpdateValidator).validate();
		service.updateAliquots(updateAliquotsDTO);

	}

	@Ignore
	@Test
	public void when_the_method_is_executed_successfully_and_the_set_of_aliquots_are_valid_then_the_laboratory_must_be_updated() throws DataNotFoundException, ValidationException {
		ParticipantLaboratoryServiceBean laboratoryService = new ParticipantLaboratoryServiceBean();
		JsonObjectUpdateAliquotsDTOFactory dtoFactory = new JsonObjectUpdateAliquotsDTOFactory();
		UpdateAliquotsDTO aliquotsDTO = UpdateAliquotsDTO.deserialize(dtoFactory.create().toString());
		
		laboratoryService.updateAliquots(aliquotsDTO);

	}

}
