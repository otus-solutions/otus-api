package br.org.otus.laboratory.participant;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface ParticipantLaboratoryDao {

	void persist(ParticipantLaboratory laboratoryParticipant);

	ParticipantLaboratory find();

	ParticipantLaboratory findByRecruitmentNumber(long rn) throws DataNotFoundException;

	ParticipantLaboratory updateLaboratoryData(ParticipantLaboratory labParticipant) throws DataNotFoundException;
	
	public Document findTubeByAliquot(String aliquotCode) throws DataNotFoundException;
	
}
