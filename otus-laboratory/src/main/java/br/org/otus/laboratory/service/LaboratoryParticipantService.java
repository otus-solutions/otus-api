package br.org.otus.laboratory.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.participant.LaboratoryParticipant;

public interface LaboratoryParticipantService {

	LaboratoryParticipant create(Long rn) throws DataNotFoundException;

	LaboratoryParticipant createEmptyLaboratory(Long rn) throws DataNotFoundException;

	LaboratoryParticipant addTubesToParticipant(Long rn) throws DataNotFoundException;
	
	boolean hasLaboratory(Long rn);
	
	LaboratoryParticipant getLaboratory(Long rn) throws DataNotFoundException;
}
