package br.org.otus.laboratory.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.collect.aliquot.Aliquot;
import br.org.otus.laboratory.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.dto.UpdateTubeAliquotsDTO;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;

public class AliquotUpdateValidator implements ParticipantLaboratoryValidator {

	private UpdateAliquotsDTO updateAliquotsDTO;
	private AliquotUpdateValidateResponse aliquotUpdateValidateResponse;
	private ParticipantLaboratoryDao participantLaboratoryDao;

	public AliquotUpdateValidator(UpdateAliquotsDTO updateAliquotsDTO, ParticipantLaboratoryDao participantLaboratoryDao) {
		this.updateAliquotsDTO = updateAliquotsDTO;
		this.aliquotUpdateValidateResponse = new AliquotUpdateValidateResponse();
		this.participantLaboratoryDao = participantLaboratoryDao;
	}

	@Override
	public AliquotUpdateValidateResponse validate() {
		getDuplicatesAliquotsOnDTO();
//		if (!aliquotUpdateValidateResponse.isValid()) {
//			throw new ValidationException(new Throwable("There are repeated aliquots on DTO."));
//		}

		verifyConflictsOnDB();
//		if (!aliquotUpdateValidateResponse.isValid()) {
//			throw new ValidationException(new Throwable("There are repeated aliquots on DataBase."));
//		}
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
				if (isAliquoted(updateAliquotsDTO.getRecruitmentNumber(), aliquot.getCode())) {
					aliquotUpdateValidateResponse.getConflicts().add(aliquot);
				}
			}
		}
		return aliquotUpdateValidateResponse.getConflicts();
	}

	private boolean isAliquoted(long rn, String aliquotCode) {
		try {
			participantLaboratoryDao.findDocumentWithAliquotCodeNotInRecruimentNumber(rn, aliquotCode);
			return true;

		} catch (DataNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

}
