package br.org.otus.laboratory.participant.validators;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.util.JsonObjecParticipantLaboratoryFactory;
import br.org.otus.laboratory.participant.util.JsonObjectUpdateAliquotsDTOFactory;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.when;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class AliquotUpdateValidatorTest {

  @InjectMocks
  private AliquotUpdateValidator aliquotUpdateValidator;
  @Mock
  private AliquotDao aliquotDao;
  @Mock
  private UpdateAliquotsDTO updateAliquotsDTO;
  @Mock
  private ParticipantLaboratory participantLaboratory;

  private AliquotUpdateValidateResponse aliquotUpdateValidateResponse = null;

  @Before
  public void setUp() throws Exception {
    updateAliquotsDTO = UpdateAliquotsDTO.deserialize(new JsonObjectUpdateAliquotsDTOFactory().create().toString());

    JsonObjecParticipantLaboratoryFactory jsonObjecParticipantLaboratoryFactory = new JsonObjecParticipantLaboratoryFactory();
    participantLaboratory = ParticipantLaboratory.deserialize(jsonObjecParticipantLaboratoryFactory.create().toString());

  }

  @Test
  public void validate_method_should_throw_a_ValidationException_with_message_duplicates_aliquots_on_DTO() throws DataNotFoundException {
    try {
      updateAliquotsDTO.getUpdateTubeAliquots().get(0).getAliquots().add(SimpleAliquot.deserialize("{code:123458}"));
      aliquotUpdateValidator = new AliquotUpdateValidator(updateAliquotsDTO, aliquotDao, participantLaboratory);
      aliquotUpdateValidateResponse = aliquotUpdateValidator.validate();
    } catch (ValidationException e) {
      assertEquals(e.getCause().getMessage(), "There are repeated aliquots on DTO.");
    }
  }

  @Test
  public void validate_method_should_throw_a_DataNotFoundException_with_message_there_all_tubes_on_participant() throws ValidationException {
    try {
      participantLaboratory.getTubes().remove(0);
      aliquotUpdateValidator = new AliquotUpdateValidator(updateAliquotsDTO, aliquotDao, participantLaboratory);
      aliquotUpdateValidateResponse = aliquotUpdateValidator.validate();
    } catch (DataNotFoundException e) {
      assertEquals(e.getCause().getMessage(), "Tube codes not found.");
    }
  }

  @Test
  public void validate_method_should_throw_a_ValidationException_with_message_verify_conflicts_on_db() throws DataNotFoundException {
    try {
      when(aliquotDao.exists(updateAliquotsDTO.getUpdateTubeAliquots().get(0).getAliquots().get(0).getCode())).thenReturn(true);
      aliquotUpdateValidator = new AliquotUpdateValidator(updateAliquotsDTO, aliquotDao, participantLaboratory);
      aliquotUpdateValidateResponse = aliquotUpdateValidator.validate();
    } catch (ValidationException e) {
      assertEquals(e.getCause().getMessage(), "There are repeated aliquots on Database.");
    }
  }


}
