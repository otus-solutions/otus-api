package org.ccem.otus.participant.persistence;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.business.extraction.model.ParticipantResultExtraction;
import org.ccem.otus.participant.model.Participant;

import com.mongodb.client.AggregateIterable;

public interface ParticipantDao {

  void persist(Participant participant);

  void update(Participant participant);

  ArrayList<Participant> find();

  Participant findByRecruitmentNumber(Long rn) throws DataNotFoundException;

  ObjectId findIdByRecruitmentNumber(Long rn) throws DataNotFoundException;

  ArrayList<Participant> findByFieldCenter(FieldCenter fieldCenter);

  Long countParticipantActivities(String centerAcronym) throws DataNotFoundException;

  void addAuthToken(String email, String Token);

  void removeAuthToken(String email, String Token);

  boolean exists(Long rn);

  Participant getLastInsertion(FieldCenter fieldCenter) throws DataNotFoundException;

  ArrayList<Long> getRecruitmentNumbersByFieldCenter(String center) throws DataNotFoundException;

  AggregateIterable<Document> aggregate(List<Bson> query);

  Participant fetchByEmail(String userEmail) throws DataNotFoundException;

  String getParticipantFieldCenterByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException;

  Participant fetchByToken(String token) throws DataNotFoundException;

  void registerPassword(String email, String password) throws DataNotFoundException;

  Participant getParticpant(ObjectId id) throws DataNotFoundException;

  Boolean updateEmail(ObjectId participantId, String email) throws DataNotFoundException, AlreadyExistException;

  String getEmail(ObjectId id) throws DataNotFoundException;

  Boolean deleteEmail(ObjectId id) throws DataNotFoundException;

  LinkedList<ParticipantResultExtraction> getParticipantExtraction() throws DataNotFoundException;
}

