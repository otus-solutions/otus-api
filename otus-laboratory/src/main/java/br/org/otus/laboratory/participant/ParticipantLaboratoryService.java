package br.org.otus.laboratory.participant;

import java.util.LinkedList;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.tube.Tube;

public interface ParticipantLaboratoryService {

  ParticipantLaboratory create(Long rn) throws DataNotFoundException;

  boolean hasLaboratory(Long rn);

  ParticipantLaboratory getLaboratory(Long rn) throws DataNotFoundException;

  Tube updateTubeCollectionData(long rn, Tube tubeToUpdate) throws DataNotFoundException;

  ParticipantLaboratory updateAliquots(UpdateAliquotsDTO updateAliquots) throws DataNotFoundException, ValidationException;

  void deleteAliquot(String code) throws ValidationException, DataNotFoundException;

  LinkedList<LaboratoryRecordExtraction> getLaboratoryExtraction() throws DataNotFoundException;

  String convertAliquotRole(Aliquot convertedAliquot) throws DataNotFoundException, ValidationException;
}
