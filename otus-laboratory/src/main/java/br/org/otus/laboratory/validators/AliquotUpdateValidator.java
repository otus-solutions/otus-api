package br.org.otus.laboratory.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.collect.aliquot.Aliquot;
import br.org.otus.laboratory.collect.tube.Tube;
import br.org.otus.laboratory.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.dto.UpdateTubeAliquotsDTO;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;

public class AliquotUpdateValidator implements ParticipantLaboratoryValidator {

	private UpdateAliquotsDTO updateAliquotsDTO;
	private AliquotUpdateValidateResponse aliquotUpdateValidateResponse;
	private ParticipantLaboratoryDao participantLaboratoryDao;
	private ParticipantLaboratory participantLaboratory;

	public AliquotUpdateValidator(UpdateAliquotsDTO updateAliquotsDTO, ParticipantLaboratoryDao participantLaboratoryDao, ParticipantLaboratory participantLaboratory) {
		this.aliquotUpdateValidateResponse = new AliquotUpdateValidateResponse();
		this.updateAliquotsDTO = updateAliquotsDTO;
		this.participantLaboratoryDao = participantLaboratoryDao;
		this.participantLaboratory = participantLaboratory;
	}

	@Override
	public AliquotUpdateValidateResponse validate() throws ValidationException, DataNotFoundException {
		getDuplicatesAliquotsOnDTO();
		if (!aliquotUpdateValidateResponse.isValid()) {
			throw new ValidationException(new Throwable("There are repeated aliquots on DTO."),
					aliquotUpdateValidateResponse);
		}

		areThereAllTubesOnParticipant();
		if (!aliquotUpdateValidateResponse.isValid()) {
			throw new DataNotFoundException(
					new Throwable("Tube codes not found."), aliquotUpdateValidateResponse.getTubesNotFound());
		}
		
		verifyConflictsOnDB();
		if (!aliquotUpdateValidateResponse.isValid()) {
			throw new ValidationException(new Throwable("There are repeated aliquots on Database."),
					aliquotUpdateValidateResponse);
		}

		return aliquotUpdateValidateResponse;
	}

	private void areThereAllTubesOnParticipant() throws DataNotFoundException {
		for (UpdateTubeAliquotsDTO tubesDTO : updateAliquotsDTO.getUpdateTubeAliquots()) {
			boolean currentTubeExists = false;
			for (Tube tube : participantLaboratory.getTubes()) {
				if (tubesDTO.getTubeCode().equals(tube.getCode())) {
					currentTubeExists = true;
					break;
				}
			}
			if (currentTubeExists == false) {
				aliquotUpdateValidateResponse.getTubesNotFound().add(tubesDTO.getTubeCode());
			}
		}
	}

	private List<Aliquot> getDuplicatesAliquotsOnDTO() {
		Set<String> set = new HashSet<>();

		for (UpdateTubeAliquotsDTO updateTubeAliquotDTO : updateAliquotsDTO.getUpdateTubeAliquots()) {
			for (Aliquot aliquot : updateTubeAliquotDTO.getAliquots()) {
				if (!set.add(aliquot.getCode())) {
					aliquotUpdateValidateResponse.getConflicts().add(aliquot);
				}
			}
		}
		return aliquotUpdateValidateResponse.getConflicts();
	}

	private List<Aliquot> verifyConflictsOnDB() {
		for (UpdateTubeAliquotsDTO aliquotDTO : updateAliquotsDTO.getUpdateTubeAliquots()) {
			for (Aliquot aliquot : aliquotDTO.getAliquots()) {
				if (isAliquoted(aliquot.getCode())) {
					aliquotUpdateValidateResponse.getConflicts().add(aliquot);
				}
			}
		}
		return aliquotUpdateValidateResponse.getConflicts();
	}

	private boolean isAliquoted(String aliquotCode) {
		try {
			participantLaboratoryDao.findDocumentByAliquotCode(aliquotCode);
			return true;

		} catch (DataNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

}
