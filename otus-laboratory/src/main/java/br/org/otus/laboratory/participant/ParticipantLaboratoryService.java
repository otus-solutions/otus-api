package br.org.otus.laboratory.participant;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.validators.AliquotUpdateValidateResponse;

public interface ParticipantLaboratoryService {

	ParticipantLaboratory create(Long rn) throws DataNotFoundException;

	boolean hasLaboratory(Long rn);

	ParticipantLaboratory getLaboratory(Long rn) throws DataNotFoundException;

	ParticipantLaboratory update(ParticipantLaboratory participantLaboratory) throws DataNotFoundException;

	ParticipantLaboratory updateAliquots(UpdateAliquotsDTO updateAliquots) throws DataNotFoundException, ValidationException;

}
