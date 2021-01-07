package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.model.ParticipantContactAttempt;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactPositionOptions;
import org.ccem.otus.participant.persistence.ParticipantContactAttemptDao;
import org.ccem.otus.participant.persistence.ParticipantContactDao;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

@Stateless
public class ParticipantContactAttemptServiceBean implements ParticipantContactAttemptService {

  @Inject
  private ParticipantContactAttemptDao participantContactAttemptDao;

  @Override
  public ObjectId create(ParticipantContactAttempt participantContactAttempt) throws DataFormatException {
    return participantContactAttemptDao.create(participantContactAttempt);
  }

  @Override
  public void delete(ObjectId participantContactOID) throws DataNotFoundException {
    participantContactAttemptDao.delete(participantContactOID);
  }

  @Override
  public ArrayList<ParticipantContactAttempt> findAttempts(Long recruitmentNumber) throws DataNotFoundException {
    return participantContactAttemptDao.findAttempts(recruitmentNumber);
  }
}
