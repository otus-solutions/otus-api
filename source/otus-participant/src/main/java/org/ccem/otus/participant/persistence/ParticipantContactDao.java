package org.ccem.otus.participant.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.participant_contact.ParticipantContact;

public interface ParticipantContactDao {

  ObjectId create(ParticipantContact participantContact);

  void update(ObjectId participantContactOID, ParticipantContact participantContact) throws DataNotFoundException;

  void delete(ObjectId participantContactOID) throws DataNotFoundException;

  ParticipantContact get(ObjectId participantContactOID) throws DataNotFoundException;

}
