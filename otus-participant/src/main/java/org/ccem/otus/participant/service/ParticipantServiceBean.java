package org.ccem.otus.participant.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

@Stateless
public class ParticipantServiceBean implements ParticipantService {

  @Inject
  private ParticipantDao participantDao;

  @Override
  public void create(Set<Participant> participants) {
    participants.forEach(participant -> create(participant));
  }

  @Override
  public void create(Participant participant) {
    participantDao.persist(participant);
  }

  @Override
  public List<Participant> list(FieldCenter fieldCenter) {
    if (fieldCenter == null) {
      return participantDao.find();
    } else {
      return participantDao.findByFieldCenter(fieldCenter);
    }
  }

  @Override
  public Participant getByRecruitmentNumber(long rn) throws DataNotFoundException {
    return participantDao.findByRecruitmentNumber(rn);
  }
  
  @Override
  public Long getPartipantsActives(String acronymCenter) throws DataNotFoundException {
    return participantDao.getPartipantsActives(acronymCenter);
  }

}
