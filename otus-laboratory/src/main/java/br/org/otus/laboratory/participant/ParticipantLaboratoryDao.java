package br.org.otus.laboratory.participant;

import java.util.ArrayList;
import java.util.LinkedList;

import br.org.otus.laboratory.participant.dto.ConvertAliquotRoleDTO;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoIterable;

import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
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
  
  LinkedList<LaboratoryRecordExtraction> getLaboratoryExtractionByParticipant();
  
  AggregateIterable<Document> aggregate(ArrayList<Bson> pipeline);

    boolean convertAliquotRole(ConvertAliquotRoleDTO convertAliquotRoleDTO);
}
