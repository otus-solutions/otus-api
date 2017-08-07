package br.org.otus.laboratory.participant;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.validators.AliquotUpdateValidateResponse;

import java.util.ArrayList;

public interface ParticipantLaboratoryService {

	ParticipantLaboratory create(Long rn) throws DataNotFoundException;

	boolean hasLaboratory(Long rn);

	ParticipantLaboratory getLaboratory(Long rn) throws DataNotFoundException;

	ParticipantLaboratory update(ParticipantLaboratory participantLaboratory) throws DataNotFoundException;

	ParticipantLaboratory updateAliquots(UpdateAliquotsDTO updateAliquots) throws DataNotFoundException, ValidationException;

	ArrayList<Aliquot> getAllAliquots();

	ArrayList<Aliquot> getAllAliquots(String fieldCenter);

}
