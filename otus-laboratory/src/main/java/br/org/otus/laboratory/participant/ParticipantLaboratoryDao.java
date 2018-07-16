package br.org.otus.laboratory.participant;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.persistence.ExamLotDao;
import br.org.otus.laboratory.project.transportation.persistence.TransportationLotDao;
import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

public interface ParticipantLaboratoryDao {

	void persist(ParticipantLaboratory laboratoryParticipant);

	ParticipantLaboratory find();

	ParticipantLaboratory findByRecruitmentNumber(long rn) throws DataNotFoundException;

	ParticipantLaboratory updateLaboratoryData(ParticipantLaboratory labParticipant) throws DataNotFoundException;

	Tube updateTubeCollectionData(long rn, Tube tube) throws DataNotFoundException;

	Document findDocumentByAliquotCode(String aliquotCode) throws DataNotFoundException;

	ArrayList<Aliquot> getFullAliquotsList();

	ArrayList<ParticipantLaboratory> getAllParticipantLaboratory();

	ArrayList<WorkAliquot> getAliquotsByPeriod(WorkAliquotFiltersDTO workAliquotFiltersDTO);	

	WorkAliquot getAliquot(WorkAliquotFiltersDTO workAliquotFiltersDTO);
	
	void deleteAliquot(String code) throws DataNotFoundException;

}
