package br.org.otus.laboratory.participant;

import java.util.List;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.collect.aliquot.Aliquot;

public interface ParticipantLaboratoryDao {

	void persist(ParticipantLaboratory laboratoryParticipant);

	ParticipantLaboratory find();

	ParticipantLaboratory findByRecruitmentNumber(long rn) throws DataNotFoundException;

	ParticipantLaboratory updateLaboratoryData(ParticipantLaboratory labParticipant) throws DataNotFoundException;

	public Document findDocumentByAliquotCode(String aliquotCode) throws DataNotFoundException;

	public ParticipantLaboratory updateTubeOnParticipanteLaboratory(ParticipantLaboratory partipantLaboratory, String tubeCode, List<Aliquot> newAliquots) throws DataNotFoundException;

}
