package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.persistence.ParticipantContactDao;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ParticipantContactServiceBean implements ParticipantContactService {

  @Inject
  private ParticipantContactDao participantContactDao;

  @Override
  public ObjectId create(ParticipantContact participantContact) {
    return participantContactDao.create(participantContact);
  }

  @Override
  public void update(ObjectId participantContactOID, ParticipantContact participantContact) throws DataNotFoundException {
    participantContactDao.update(participantContactOID, participantContact);
  }

  @Override
  public void delete(ObjectId participantContactOID) throws DataNotFoundException {
    participantContactDao.delete(participantContactOID);
  }

  @Override
  public ParticipantContact get(ObjectId participantContactOID) throws DataNotFoundException {
    return participantContactDao.get(participantContactOID);
  }
}
