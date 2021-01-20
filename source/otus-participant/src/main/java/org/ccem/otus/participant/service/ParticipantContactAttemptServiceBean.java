package org.ccem.otus.participant.service;

import br.org.otus.model.User;
import br.org.otus.persistence.UserDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAddressAttempt;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttemptConfiguration;
import org.ccem.otus.participant.model.participantContactAttempt.ParticipantContactAttempt;
import org.ccem.otus.participant.model.participant_contact.Address;
import org.ccem.otus.participant.persistence.ParticipantContactAttemptConfigurationDao;
import org.ccem.otus.participant.persistence.ParticipantContactAttemptDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

@Stateless
public class ParticipantContactAttemptServiceBean implements ParticipantContactAttemptService {

  @Inject
  private ParticipantContactAttemptDao participantContactAttemptDao;

  @Inject
  private ParticipantContactAttemptConfigurationDao metadataAttemptStatusDao;

  @Inject
  private UserDao userDao;


  @Override
  public ObjectId create(ParticipantContactAttempt participantContactAttempt, String userEmail) throws DataFormatException, DataNotFoundException {
    User user = userDao.fetchByEmail(userEmail);
    participantContactAttempt.setRegisteredBy(user.get_id());
    return participantContactAttemptDao.create(participantContactAttempt);
  }

  @Override
  public void delete(ObjectId participantContactOID) throws DataNotFoundException {
    participantContactAttemptDao.delete(participantContactOID);
  }

  @Override
  public void updateAttemptAddress(Long recruitmentNumber, String objectType, String position, Address addresses) throws DataNotFoundException {
    participantContactAttemptDao.updateAttemptAddress(recruitmentNumber, objectType, position, addresses);
  }

  @Override
  public   void changeAddress(Long recruitmentNumber, String objectType, String position) throws DataNotFoundException{
    participantContactAttemptDao.changeAddress(recruitmentNumber, objectType, position);
  }

  @Override
  public ArrayList<ParticipantContactAddressAttempt> findAddressAttempts(Long recruitmentNumber, String objectType, String position) throws DataNotFoundException {
    return participantContactAttemptDao.findAddressAttempts(recruitmentNumber, objectType, position);
  }

  @Override
  public ParticipantContactAttemptConfiguration findMetadataAttempt(String objectType) throws DataNotFoundException {
    return metadataAttemptStatusDao.findMetadataAttempt(objectType);
  }

}
