package br.org.otus.laboratory.persistence;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.participant.LaboratoryParticipant;
import br.org.otus.laboratory.participant.Tube;

public interface LaboratoryParticipantDao {

	void persist(LaboratoryParticipant laboratoryParticipant);

	LaboratoryParticipant find();

	Tube findByCode(Integer code);

	LaboratoryParticipant findByRecruitmentNumber(long rn) throws DataNotFoundException;

	LaboratoryParticipant updateLaboratoryData(LaboratoryParticipant labParticipant) throws DataNotFoundException;
	
}
