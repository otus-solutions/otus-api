package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.persistence.ParticipantContactDao;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.zip.DataFormatException;

@Stateless
public class ParticipantContactServiceBean implements ParticipantContactService {

  @Inject
  private ParticipantContactDao participantContactDao;

  @Override
  public ObjectId create(ParticipantContact participantContact) {
    return participantContactDao.create(participantContact);
  }

  @Override
  public void update(ParticipantContact participantContact) throws DataNotFoundException {
    participantContactDao.update(participantContact);
  }

  @Override
  public void updateMainContact(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDto(participantContactDto);
    participantContactDao.updateMainContact(participantContactDto);
  }

  @Override
  public void addSecondaryContact(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDto(participantContactDto);
    participantContactDao.addSecondaryContact(participantContactDto);
  }

  @Override
  public void updateSecondaryContact(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDto(participantContactDto);
    participantContactDao.updateSecondaryContact(participantContactDto);
  }

  @Override
  public void swapMainContactWithSecondary(ParticipantContactDto participantContactDto) throws DataNotFoundException {

  }

  @Override
  public void delete(ObjectId participantContactOID) throws DataNotFoundException {
    participantContactDao.delete(participantContactOID);
  }

  @Override
  public void deleteSecondaryContact(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDto(participantContactDto);
    participantContactDao.deleteSecondaryContact(participantContactDto);
  }

  @Override
  public ParticipantContact get(ObjectId participantContactOID) throws DataNotFoundException {
    return participantContactDao.get(participantContactOID);
  }

  @Override
  public ParticipantContact getByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
    return participantContactDao.getByRecruitmentNumber(recruitmentNumber);
  }

  private void validateDto(ParticipantContactDto participantContactDto) throws DataFormatException {
    if(!participantContactDto.isValid()){
      throw new DataFormatException("ParticipantContactDto is invalid");
    }
  }
}
