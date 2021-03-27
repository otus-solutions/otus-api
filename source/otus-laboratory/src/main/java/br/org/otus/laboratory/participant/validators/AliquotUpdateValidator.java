package br.org.otus.laboratory.participant.validators;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.transportation.persistence.MaterialTrackingDao;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.dto.UpdateTubeAliquotsDTO;
import br.org.otus.laboratory.participant.tube.Tube;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import br.org.otus.laboratory.project.transportation.MaterialTrail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AliquotUpdateValidator implements ParticipantLaboratoryValidator {

  private UpdateAliquotsDTO updateAliquotsDTO;
  private AliquotUpdateValidateResponse aliquotUpdateValidateResponse;
  private AliquotDao aliquotDao;
  private ParticipantLaboratory participantLaboratory;
  private MaterialTrackingDao materialTrackingDao;

  public AliquotUpdateValidator(UpdateAliquotsDTO updateAliquotsDTO, AliquotDao aliquotDao, ParticipantLaboratory participantLaboratory) {
    this.aliquotUpdateValidateResponse = new AliquotUpdateValidateResponse();
    this.updateAliquotsDTO = updateAliquotsDTO;
    this.aliquotDao = aliquotDao;
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

    checkReceivedMaterial();
    if (!aliquotUpdateValidateResponse.isValid()) {
      throw new ValidationException(
        new Throwable("Aliquot deletion unauthorized, material has already been received."),
        aliquotUpdateValidateResponse
      );
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

  private List<SimpleAliquot> getDuplicatesAliquotsOnDTO() {
    Set<String> set = new HashSet<>();

    for (UpdateTubeAliquotsDTO updateTubeAliquotDTO : updateAliquotsDTO.getUpdateTubeAliquots()) {
      for (SimpleAliquot aliquot : updateTubeAliquotDTO.getAliquots()) {
        if (!set.add(aliquot.getCode())) {
          aliquotUpdateValidateResponse.getConflicts().add(aliquot);
        }
      }
    }
    return aliquotUpdateValidateResponse.getConflicts();
  }

  private List<SimpleAliquot> verifyConflictsOnDB() {
    for (UpdateTubeAliquotsDTO aliquotDTO : updateAliquotsDTO.getUpdateTubeAliquots()) {
      for (SimpleAliquot aliquot : aliquotDTO.getAliquots()) {
        if (isAliquoted(aliquot.getCode())) {
          aliquotUpdateValidateResponse.getConflicts().add(aliquot);
        }
      }
    }
    return aliquotUpdateValidateResponse.getConflicts();
  }

  private boolean isAliquoted(String aliquotCode) {
    return aliquotDao.exists(aliquotCode);
  }

  private List<SimpleAliquot> checkReceivedMaterial() {
    for (UpdateTubeAliquotsDTO aliquotDTO : updateAliquotsDTO.getUpdateTubeAliquots()) {
      for (SimpleAliquot aliquot : aliquotDTO.getAliquots()) {
        MaterialTrail materialTrail = materialTrackingDao.getCurrent(aliquot.getCode());

        if (materialTrail.getReceived()) {
          aliquotUpdateValidateResponse.getConflicts().add(aliquot);
        }
      }
    }
    return aliquotUpdateValidateResponse.getConflicts();
  }

}
