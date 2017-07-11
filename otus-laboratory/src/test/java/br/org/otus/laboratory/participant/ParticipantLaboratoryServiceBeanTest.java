package br.org.otus.laboratory.participant;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.org.otus.laboratory.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.util.JsonObjectUpdateAliquotsDTOFactory;

import com.google.gson.JsonObject;

@RunWith(JUnit4.class)
public class ParticipantLaboratoryServiceBeanTest {

	@Mock
	private UpdateAliquotsDTO updateAliquotsDTO;

	private ParticipantLaboratoryService laboratoryService;

	private JsonObjectUpdateAliquotsDTOFactory jsonObjectUpdateAliquotsDTOFactory;
	private JsonObject jsonObjectUpdateAliquotsDTO;

	@Before
	public void setup() {
		jsonObjectUpdateAliquotsDTOFactory = new JsonObjectUpdateAliquotsDTOFactory();
		jsonObjectUpdateAliquotsDTO = new JsonObject();
		jsonObjectUpdateAliquotsDTO = jsonObjectUpdateAliquotsDTOFactory.create();

		updateAliquotsDTO = UpdateAliquotsDTO.deserialize(jsonObjectUpdateAliquotsDTO.toString());
		laboratoryService = new ParticipantLaboratoryServiceBean();
	}

	@Test
	public void updateAliquots_method_should_call_methed_validate() throws DataNotFoundException, ValidationException {
		Mockito.when(laboratoryService.hasLaboratory(Mockito.anyLong())).thenReturn(true);

		laboratoryService.updateAliquots(updateAliquotsDTO);

	}

}
