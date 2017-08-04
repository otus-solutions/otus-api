package br.org.otus.laboratory.validators;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.util.JsonObjecParticipantLaboratoryFactory;
import br.org.otus.laboratory.participant.util.JsonObjectUpdateAliquotsDTOBuilder;
import br.org.otus.laboratory.participant.util.JsonObjectUpdateAliquotsDTOFactory;
import br.org.otus.laboratory.participant.validators.AliquotUpdateValidator;

@Ignore
@RunWith(PowerMockRunner.class)
public class AliquotUpdateValidatorTest {

	@InjectMocks
	private AliquotUpdateValidator aliquotUpdateValidator;
	@Mock
	private ParticipantLaboratoryDao participantLaboratoryDao;

	private UpdateAliquotsDTO updateAliquotsDTO;
	private ParticipantLaboratory participantLaboratory;

	@Before
	public void setUp() throws Exception {
		updateAliquotsDTO = UpdateAliquotsDTO.deserialize(new JsonObjectUpdateAliquotsDTOFactory().create().toString());
		System.out.println(new JsonObjecParticipantLaboratoryFactory().create());
		System.out.println(UpdateAliquotsDTO.serialize(updateAliquotsDTO));
//		new AliquotUpdateValidator(updateAliquotsDTO, participantLaboratoryDao, participantLaboratory);
	}
	
	@Test
	public void validate_method_should_throw_a_ValidationException_with_message_duplicates_aliquotes_on_DTO() throws Exception {
		JsonObjectUpdateAliquotsDTOBuilder builder = new JsonObjectUpdateAliquotsDTOBuilder(100);
		builder.addTubeCode("20000")
			   .withAliquot("12345");
//		aliquotUpdateValidator.validate();
	}


}
