package org.ccem.otus.participant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;

@Stateless
public class ParticipantServiceBean implements ParticipantService {

  @Inject
  private ParticipantDao participantDao;
  
  @Override
  public void create(Set<Participant> participants) {
    ArrayList<Participant> insertedParticipants = new ArrayList<>();
    participants.forEach(participant -> {
      try {
        insertedParticipants.add(create(participant));
      } catch (ValidationException e) {
        insertedParticipants.add(null);
      }

    });
  }

  @Override
  public Participant create(Participant participant) throws ValidationException{
    Participant rn = participantDao.validateRecruitmentNumber(participant.getRecruitmentNumber());
    if(rn != null) {
      String error = "RecruimentNumber {"+ rn.getRecruitmentNumber().toString() +"} already exist.";
      throw new ValidationException(new Throwable(error));
    }else {
      participantDao.persist(participant);
      return participant;  
    } 
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
  public Participant getByRecruitmentNumber(Long rn) throws DataNotFoundException {
    return participantDao.findByRecruitmentNumber(rn);
  }

  @Override
  public Long getPartipantsActives(String acronymCenter) throws DataNotFoundException {
    return participantDao.getPartipantsActives(acronymCenter);
  }

}
