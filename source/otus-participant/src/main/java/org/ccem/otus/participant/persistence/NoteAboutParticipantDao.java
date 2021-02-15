package org.ccem.otus.participant.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantDto;

import java.util.List;

public interface NoteAboutParticipantDao {

  ObjectId create(NoteAboutParticipant commentAboutParticipant);

  ObjectId update(NoteAboutParticipant commentAboutParticipant) throws DataNotFoundException;

  void delete(ObjectId userId, ObjectId noteAboutParticipantId) throws DataNotFoundException, ValidationException;

  List<NoteAboutParticipantDto> getAll(ObjectId userOid, Long recruitmentNumber, int skip, int limit) throws MemoryExcededException;
}
