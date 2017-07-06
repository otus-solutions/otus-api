package br.org.otus.laboratory.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.collect.aliquot.Aliquot;
import br.org.otus.laboratory.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.dto.UpdateTubeAliquotsDTO;
import br.org.otus.laboratory.participant.ParticipantLaboratoryService;

public class AliquotUpdateValidator implements ParticipantLaboratoryValidator {

	@Inject
	private ParticipantLaboratoryService service;
	private UpdateAliquotsDTO updateAliquotsDTO;
	private AliquotUpdateValidateResponse aliquotUpdateValidateResponse;

	public AliquotUpdateValidator(UpdateAliquotsDTO updateAliquotsDTO) {
		this.updateAliquotsDTO = updateAliquotsDTO;
		this.aliquotUpdateValidateResponse = new AliquotUpdateValidateResponse();
	}

	@Override
	public AliquotUpdateValidateResponse validate() throws ValidationException {
		getDuplicatesAliquotsOnDTO();
		if (!aliquotUpdateValidateResponse.isValid()) {
			throw new ValidationException();
		}

		verifyConflictsOnDB();
		if (!aliquotUpdateValidateResponse.isValid()) {
			throw new ValidationException();
		}

		return aliquotUpdateValidateResponse;
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
				if (service.isAliquoted(updateAliquotsDTO.getRecruitmentNumber(), aliquot.getCode())) {
					aliquotUpdateValidateResponse.getConflicts().add(aliquot);
				}
			}
		}
		return aliquotUpdateValidateResponse.getConflicts();
	}
}
