package org.ccem.otus.participant.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.ccem.otus.participant.model.comment.NoteAboutParticipantResponse;

import java.util.List;

public interface NoteAboutParticipantDao {

  ObjectId create(NoteAboutParticipant commentAboutParticipant);

  NoteAboutParticipant get(ObjectId noteAboutParticipantId);

  void update(ObjectId userOid, NoteAboutParticipant commentAboutParticipant) throws DataNotFoundException;

  void updateStarred(ObjectId userId, ObjectId noteAboutParticipantId, boolean starred) throws DataNotFoundException, ValidationException;

  void delete(ObjectId userId, ObjectId noteAboutParticipantId) throws DataNotFoundException;

  List<NoteAboutParticipantResponse> getAll(ObjectId userOid, Long recruitmentNumber, int skip, int limit) throws MemoryExcededException;
}
