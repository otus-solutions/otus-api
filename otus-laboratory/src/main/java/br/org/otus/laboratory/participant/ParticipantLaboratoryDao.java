package br.org.otus.laboratory.participant;

import java.util.ArrayList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.tube.Tube;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.transportation.persistence.WorkAliquotFiltersDTO;

public interface ParticipantLaboratoryDao {

  void persist(ParticipantLaboratory laboratoryParticipant);

  ParticipantLaboratory find();

  ParticipantLaboratory findByRecruitmentNumber(long rn) throws DataNotFoundException;

  ParticipantLaboratory updateLaboratoryData(ParticipantLaboratory labParticipant) throws DataNotFoundException;

  Tube updateTubeCollectionData(long rn, Tube tube) throws DataNotFoundException;

  ParticipantLaboratory findParticipantLaboratory(String aliquotCode) throws DataNotFoundException;

  ArrayList<Aliquot> getFullAliquotsList();

  ArrayList<ParticipantLaboratory> getAllParticipantLaboratory();

  ArrayList<WorkAliquot> getAliquotsByPeriod(WorkAliquotFiltersDTO workAliquotFiltersDTO);

  WorkAliquot getAliquot(WorkAliquotFiltersDTO workAliquotFiltersDTO);

}
