package br.org.otus.laboratory.participant;

import java.util.ArrayList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.participant.tube.Tube;

public interface ParticipantLaboratoryDao {

  void persist(ParticipantLaboratory laboratoryParticipant);

  ParticipantLaboratory find();

  ParticipantLaboratory findByRecruitmentNumber(long rn) throws DataNotFoundException;

  Tube updateTubeCollectionData(long rn, Tube tube) throws DataNotFoundException;

  ParticipantLaboratory findParticipantLaboratory(String aliquotCode) throws DataNotFoundException;

  ArrayList<SimpleAliquot> getFullAliquotsList();

  ArrayList<ParticipantLaboratory> getAllParticipantLaboratory();

}
