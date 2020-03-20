package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.model.participant_contact.ParticipantContactPositionOptions;
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
    //TODO check if main values are not null
    return participantContactDao.create(participantContact);
  }

  @Override
  public void updateEmail(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDto(participantContactDto);
    participantContactDao.updateEmail(participantContactDto);
  }

  @Override
  public void updateAddress(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDto(participantContactDto);
    participantContactDao.updateAddress(participantContactDto);
  }

  @Override
  public void updatePhoneNumber(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDto(participantContactDto);
    participantContactDao.updatePhoneNumber(participantContactDto);
  }

  @Override
  public void addNonMainEmail(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDtoForAdd(participantContactDto);
    participantContactDao.addNonMainEmail(participantContactDto);
  }

  @Override
  public void addNonMainAddress(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDtoForAdd(participantContactDto);
    participantContactDao.addNonMainAddress(participantContactDto);
  }

  @Override
  public void addNonMainPhoneNumber(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDtoForAdd(participantContactDto);
    participantContactDao.addNonMainPhoneNumber(participantContactDto);
  }

  @Override
  public void swapMainContactWithSecondary(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDto(participantContactDto);
    participantContactDao.swapMainContactWithSecondary(participantContactDto);
  }

  @Override
  public void delete(ObjectId participantContactOID) throws DataNotFoundException {
    participantContactDao.delete(participantContactOID);
  }

  @Override
  public void deleteNonMainContact(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException {
    validateDto(participantContactDto);
    participantContactDao.deleteNonMainContact(participantContactDto);
  }

  @Override
  public ParticipantContact getParticipantContact(ObjectId participantContactOID) throws DataNotFoundException {
    return participantContactDao.getParticipantContact(participantContactOID);
  }

  @Override
  public ParticipantContact getParticipantContactByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException {
    return participantContactDao.getParticipantContactByRecruitmentNumber(recruitmentNumber);
  }

  private void validateDto(ParticipantContactDto participantContactDto) throws DataFormatException {
    if(!participantContactDto.isValid()){
      throw new DataFormatException("ParticipantContactDto is invalid");
    }
  }

  private void validateDtoForAdd(ParticipantContactDto participantContactDto) throws DataFormatException {
    if(participantContactDto.getPosition().equals(ParticipantContactPositionOptions.MAIN.getName()) || !participantContactDto.isValid()){
      throw new DataFormatException("ParticipantContactDto is invalid");
    }
  }
}
