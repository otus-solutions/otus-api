package org.ccem.otus.participant.persistence;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;

import com.mongodb.client.AggregateIterable;

public interface ParticipantDao {

  void persist(Participant participant);

  ArrayList<Participant> find();

  Participant findByRecruitmentNumber(Long rn) throws DataNotFoundException;

  ArrayList<Participant> findByFieldCenter(FieldCenter fieldCenter);

  Long countParticipantActivities(String centerAcronym) throws DataNotFoundException;

  boolean exists(Long rn);

  Participant getLastInsertion(FieldCenter fieldCenter) throws DataNotFoundException;

  ArrayList<Long> getCenterRns(String center) throws DataNotFoundException;

  AggregateIterable<Document> aggregate(List<Bson> query);

}