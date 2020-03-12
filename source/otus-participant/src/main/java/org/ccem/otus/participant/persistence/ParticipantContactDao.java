package org.ccem.otus.participant.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;
import org.ccem.otus.participant.persistence.dto.ParticipantContactDto;

import java.util.zip.DataFormatException;

public interface ParticipantContactDao {

  ObjectId create(ParticipantContact participantContact);

  void updateMainContact(ParticipantContactDto participantContactDto) throws DataNotFoundException;

  void addSecondaryContact(ParticipantContactDto participantContactDto) throws DataNotFoundException;

  void updateSecondaryContact(ParticipantContactDto participantContactDto) throws DataNotFoundException;

  void swapMainContactWithSecondary(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  void delete(ObjectId participantContactOID) throws DataNotFoundException;

  void deleteSecondaryContact(ParticipantContactDto participantContactDto) throws DataNotFoundException, DataFormatException;

  ParticipantContact get(ObjectId participantContactOID) throws DataNotFoundException;

  ParticipantContact getByRecruitmentNumber(Long recruitmentNumber) throws DataNotFoundException;

}
