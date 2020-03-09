package br.org.otus.laboratory.participant;

import java.util.ArrayList;
import java.util.LinkedList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.client.AggregateIterable;

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

  Tube getTube(String tubeCode) throws DataNotFoundException;

  ObjectId getTubeLocationPoint(String tubeCode) throws DataNotFoundException;

  ArrayList<Tube> getTubes(ArrayList<String> tubeCodeList);
}
