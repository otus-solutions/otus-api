package br.org.otus.laboratory.service;

import br.org.otus.laboratory.participant.LaboratoryParticipant;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface LaboratoryParticipantService {

	LaboratoryParticipant create(Long rn) throws DataNotFoundException;

	LaboratoryParticipant createEmptyLaboratory(Long rn) throws DataNotFoundException;

	LaboratoryParticipant addTubesToParticipant(Long rn) throws DataNotFoundException;
	
	boolean hasLaboratory(Long rn);
	
	LaboratoryParticipant getLaboratory(Long rn) throws DataNotFoundException;
}
