package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;

import java.util.zip.DataFormatException;

public interface ParticipantContactService {

  ObjectId create(ParticipantContact participantContact) throws DataFormatException;

  void addNonMainEmail(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  void addNonMainAddress(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  void addNonMainPhoneNumber(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  void updateEmail(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  void updateAddress(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  void updatePhoneNumber(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  void swapMainContactWithSecondary(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  void delete(ObjectId participantContactOID) throws DataNotFoundException;

  void deleteNonMainContact(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  ParticipantContact getParticipantContact(ObjectId participantContactOID) throws DataNotFoundException;

  ParticipantContact getParticipantContactByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException;
}
