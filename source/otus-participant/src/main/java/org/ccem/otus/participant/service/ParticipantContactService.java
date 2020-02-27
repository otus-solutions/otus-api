package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;

public interface ParticipantContactService {

  ObjectId create(ParticipantContact participantContact);

  void update(ObjectId participantContactOID, ParticipantContact participantContact) throws DataNotFoundException;

  void updateMainContact(ParticipantContactDto participantContactDto) throws DataNotFoundException;

  void addSecondaryContact(ParticipantContactDto participantContactDto) throws DataNotFoundException;

  void updateSecondaryContact(ParticipantContactDto participantContactDto) throws DataNotFoundException;

  void swapMainContactWithSecondary(ParticipantContactDto participantContactDto) throws DataNotFoundException;

  void delete(ObjectId participantContactOID) throws DataNotFoundException;

  void deleteSecondaryContact(ParticipantContactDto participantContactDto) throws DataNotFoundException;

  ParticipantContact get(ObjectId participantContactOID) throws DataNotFoundException;

  ParticipantContact getByRecruitmentNumber(String recruitmentNumber) throws DataNotFoundException;
}
