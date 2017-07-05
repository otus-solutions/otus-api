package br.org.otus.laboratory.participant;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.dto.UpdateAliquotsDTO;

public interface ParticipantLaboratoryDao {

	void persist(ParticipantLaboratory laboratoryParticipant);

	void updateAliquots(UpdateAliquotsDTO updateAliquots);

	ParticipantLaboratory find();

	ParticipantLaboratory findByRecruitmentNumber(long rn) throws DataNotFoundException;

	ParticipantLaboratory updateLaboratoryData(ParticipantLaboratory labParticipant) throws DataNotFoundException;

	public Document findDocumentWithAliquotCodeNotInRecruimentNumber(long rn, String aliquotCode) throws DataNotFoundException;

}
