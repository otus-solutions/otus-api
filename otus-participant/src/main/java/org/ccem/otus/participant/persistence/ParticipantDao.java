package org.ccem.otus.participant.persistence;

import java.util.ArrayList;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Monitoring;
import org.ccem.otus.participant.model.Participant;

public interface ParticipantDao {

  void persist(Participant participant);

  ArrayList<Participant> find();

  Participant findByRecruitmentNumber(Long rn) throws DataNotFoundException;

  ArrayList<Participant> findByFieldCenter(FieldCenter fieldCenter);

  ArrayList<Monitoring> getMonitoring();

}
